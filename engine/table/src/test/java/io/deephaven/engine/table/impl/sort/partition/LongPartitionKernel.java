//
// Copyright (c) 2016-2025 Deephaven Data Labs and Patent Pending
//
// ****** AUTO-GENERATED CLASS - DO NOT EDIT MANUALLY
// ****** Edit CharPartitionKernel and run "./gradlew replicateSortKernel" to regenerate
//
// @formatter:off
package io.deephaven.engine.table.impl.sort.partition;

import io.deephaven.chunk.attributes.Any;
import io.deephaven.engine.rowset.chunkattributes.RowKeys;
import io.deephaven.tuple.generated.LongLongTuple;
import io.deephaven.engine.table.impl.sort.timsort.LongLongTimsortKernel;
import io.deephaven.engine.table.impl.sort.LongSortKernel;
import io.deephaven.engine.table.ColumnSource;
import io.deephaven.chunk.*;
import io.deephaven.engine.rowset.RowSet;
import io.deephaven.engine.rowset.RowSetBuilderSequential;
import io.deephaven.engine.rowset.RowSetFactory;
import io.deephaven.util.SafeCloseable;

import java.util.stream.IntStream;

public class LongPartitionKernel {
    public static class PartitionKernelContext implements SafeCloseable {
        // during the actual partition operation, we stick the new keys in here; when we exceed chunksize afterwards,
        // we can pass the entire chunk value to the builder; which then makes the virtual call to build it all at once
        private final WritableLongChunk<RowKeys>[] accumulatedKeys;
        private final RowSetBuilderSequential[] builders;

        private final int chunkSize;
        private final WritableLongChunk<Any> pivotValues;
        private final WritableLongChunk<RowKeys> pivotKeys;
        private final boolean preserveEquality;

        private PartitionKernelContext(int chunkSize, int numPartitions, boolean preserveEquality) {
            this.chunkSize = chunkSize;
            this.preserveEquality = preserveEquality;

            pivotValues = WritableLongChunk.makeWritableChunk(numPartitions - 1);
            pivotKeys = WritableLongChunk.makeWritableChunk(numPartitions - 1);
            if (preserveEquality) {
                // noinspection unchecked
                accumulatedKeys = new WritableLongChunk[numPartitions * 2 - 1];
                builders = new RowSetBuilderSequential[numPartitions * 2 - 1];
            } else {
                // noinspection unchecked
                accumulatedKeys = new WritableLongChunk[numPartitions];
                builders = new RowSetBuilderSequential[numPartitions];
            }
            for (int ii = 0; ii < builders.length; ++ii) {
                builders[ii] = RowSetFactory.builderSequential();
                accumulatedKeys[ii] = WritableLongChunk.makeWritableChunk(chunkSize);
                accumulatedKeys[ii].setSize(0);
            }
        }

        public RowSet[] getPartitions(boolean resetBuilders) {
            final RowSet[] partitions = new RowSet[builders.length];
            flushAllToBuilders(this);
            for (int ii = 0; ii < builders.length; ++ii) {
                partitions[ii] = builders[ii].build();
                if (resetBuilders) {
                    builders[ii] = RowSetFactory.builderSequential();
                } else {
                    builders[ii] = null;
                }
            }
            return partitions;
        }

        // public void showPivots() {
        // System.out.println("[" + IntStream.range(0,
        // pivotValues.size()).mapToObj(pivotValues::get).map(LongPartitionKernel::format).collect(Collectors.joining(","))
        // + "]");
        // System.out.println("[" + IntStream.range(0,
        // pivotKeys.size()).mapToObj(pivotKeys::get).map(Object::toString).collect(Collectors.joining(",")) + "]");
        // }

        public LongLongTuple[] getPivots() {
            return IntStream.range(0, pivotValues.size())
                    .mapToObj(ii -> new LongLongTuple(pivotValues.get(ii), pivotKeys.get(ii)))
                    .toArray(LongLongTuple[]::new);
        }

        @Override
        public void close() {
            for (WritableLongChunk<RowKeys> chunk : accumulatedKeys) {
                chunk.close();
            }
            pivotValues.close();
            pivotKeys.close();
        }
    }

    // private static String format(long last) {
    // if (last >= 'A' && last <= 'Z') {
    // return Long.toString(last);
    // }
    // return String.format("0x%04x", (int) last);
    // }

    public static PartitionKernelContext createContext(RowSet rowSet, ColumnSource<Long> columnSource,
            int chunkSize, int nPartitions, boolean preserveEquality) {
        final PartitionKernelContext context = new PartitionKernelContext(chunkSize, nPartitions, preserveEquality);

        try (final WritableLongChunk<RowKeys> tempPivotKeys = WritableLongChunk.makeWritableChunk(nPartitions * 3);
                final WritableLongChunk<Any> tempPivotValues = WritableLongChunk.makeWritableChunk(nPartitions * 3)) {

            samplePivots(rowSet, nPartitions, tempPivotKeys, tempPivotValues, columnSource);

            // copy from the oversized chunk, which was used for sorting into the chunk which we will use for our binary
            // searches
            for (int ii = 0; ii < tempPivotKeys.size(); ++ii) {
                context.pivotKeys.set(ii, tempPivotKeys.get(ii));
                context.pivotValues.set(ii, tempPivotValues.get(ii));
            }

            return context;
        }
    }

    // the sample pivots function could be smarter; in that if we are reading a block, there is a strong argument to
    // sample the entirety of the relevant values within the block from disk. We might also want to do a complete
    // linear pass so that we can determine ideal pivots (or maybe if a radix based approach is better).
    private static void samplePivots(RowSet rowSet, int nPartitions, WritableLongChunk<RowKeys> pivotKeys,
            WritableLongChunk<Any> pivotValues, ColumnSource<Long> columnSource) {
        pivotKeys.setSize(0);
        final int pivotsRequired = nPartitions - 1;
        final int samplesRequired = pivotsRequired * 3;
        PartitionUtils.sampleIndexKeys(0, rowSet, samplesRequired, pivotKeys);

        final RowSetBuilderSequential builder = RowSetFactory.builderSequential();
        for (int ii = 0; ii < pivotKeys.size(); ++ii) {
            builder.appendKey(pivotKeys.get(ii));
        }
        final RowSet rowSetOfPivots = builder.build();
        try (final ColumnSource.FillContext context = columnSource.makeFillContext(samplesRequired)) {
            columnSource.fillChunk(context, pivotValues, rowSetOfPivots);
        }

        try (final LongSortKernel sortContext = LongLongTimsortKernel.createContext(samplesRequired)) {
            sortContext.sort(pivotKeys, pivotValues);
        }

        // now we have a thing that is sorted, we pick every third thing, starting with the second
        int ii, jj;
        for (ii = 0, jj = 1; jj < pivotKeys.size(); ii++, jj += 3) {
            pivotKeys.set(ii, pivotKeys.get(jj));
            pivotValues.set(ii, pivotValues.get(jj));
        }
        pivotKeys.setSize(ii);
    }

    /**
     * After we have created the context, we can determine what things are in a partition.
     *
     * @param context our context, containing the pivots
     * @param indexKeys a chunk of row keys to partition
     * @param values a chunk of values that go with the row keys
     */
    public static void partition(PartitionKernelContext context, LongChunk<RowKeys> indexKeys, LongChunk values) {
        final int accumulatedChunkSize = context.chunkSize;
        for (int ii = 0; ii < values.size(); ii += accumulatedChunkSize) {
            final int last = Math.min(values.size(), ii + accumulatedChunkSize);
            for (int jj = ii; jj < last; jj++) {
                // find value in the context's pivotKeys
                final long searchValue = values.get(jj);
                final long searchKey = indexKeys.get(jj);

                final int partition;
                if (context.preserveEquality) {
                    partition = binarySearchPreserve(context.pivotValues, 0, context.pivotValues.size(), searchValue);
                } else {
                    partition = binarySearchTieIndex(context.pivotValues, context.pivotKeys, 0,
                            context.pivotValues.size(), searchValue, searchKey);
                }
                context.accumulatedKeys[partition].add(searchKey);
                if (context.accumulatedKeys[partition].size() == accumulatedChunkSize) {
                    flushToBuilder(context, partition);
                }
            }
        }
        flushAllToBuilders(context);
    }

    private static void flushAllToBuilders(PartitionKernelContext context) {
        for (int ii = 0; ii < context.accumulatedKeys.length; ++ii) {
            flushToBuilder(context, ii);
        }
    }

    private static void flushToBuilder(PartitionKernelContext context, int partition) {
        final RowSetBuilderSequential builder = context.builders[partition];
        final WritableLongChunk<RowKeys> partitionKeys = context.accumulatedKeys[partition];
        final int chunkSize = partitionKeys.size();
        for (int ii = 0; ii < chunkSize; ++ii) {
            builder.appendKey(partitionKeys.get(ii));
        }
        partitionKeys.setSize(0);
    }

    private static int binarySearchPreserve(LongChunk pivotValues, int lo, int hi, long searchValue) {
        while (lo != hi) {
            final int mid = (lo + hi) / 2;
            final long compareValue = pivotValues.get(mid);
            if (eq(searchValue, compareValue)) {
                return mid * 2 + 1;
            } else if (lt(searchValue, compareValue)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo * 2;
    }

    private static int binarySearchTieIndex(LongChunk pivotValues, LongChunk<RowKeys> pivotKeys, int lo, int hi,
            long searchValue, long searchKey) {
        while (lo != hi) {
            final int mid = (lo + hi) / 2;
            final long compareValue = pivotValues.get(mid);
            if (eq(searchValue, compareValue)) {
                // we must break the tie using the pivotKeys, which is guaranteed to be unique
                final long compareKey = pivotKeys.get(mid);
                if (searchKey <= compareKey) {
                    hi = mid;
                } else {
                    lo = mid + 1;
                }
            } else if (lt(searchValue, compareValue)) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return lo;
    }

    // region comparison functions
    private static int doComparison(long lhs, long rhs) {
        return Long.compare(lhs, rhs);
    }
    // endregion comparison functions

    // region equality function
    private static boolean eq(long lhs, long rhs) {
        return lhs == rhs;
    }
    // endregion equality function

    private static boolean lt(long lhs, long rhs) {
        return doComparison(lhs, rhs) < 0;
    }
}
