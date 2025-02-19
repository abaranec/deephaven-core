//
// Copyright (c) 2016-2025 Deephaven Data Labs and Patent Pending
//
// ****** AUTO-GENERATED CLASS - DO NOT EDIT MANUALLY
// ****** Edit TestCharTimSortKernel and run "./gradlew replicateSortKernelTests" to regenerate
//
// @formatter:off
package io.deephaven.engine.table.impl.sort.timsort;

import io.deephaven.test.types.ParallelTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(ParallelTest.class)
public class TestIntTimSortKernel extends BaseTestIntTimSortKernel {
    // I like this output, but for now am leaving these tests off, so we can focus on getting right answers and we can
    // try
    // out JMH for running morally equivalent things.

    // @Test
    // public void intRandomPerformanceTest() {
    // performanceTest(TestIntTimSortKernel::generateIntRandom, IntSortKernelStuff::new, IntSortKernelStuff::run,
    // getJavaComparator(), IntMergeStuff::new, IntMergeStuff::run);
    // }
    //
    // @Test
    // public void intRunPerformanceTest() {
    // performanceTest(TestIntTimSortKernel::generateIntRuns, IntSortKernelStuff::new, IntSortKernelStuff::run,
    // getJavaComparator(), IntMergeStuff::new, IntMergeStuff::run);
    // }
    //
    // @Test
    // public void intRunDescendingPerformanceTest() {
    // performanceTest(TestIntTimSortKernel::generateDescendingIntRuns, IntSortKernelStuff::new,
    // IntSortKernelStuff::run, getJavaComparator(), IntMergeStuff::new, IntMergeStuff::run);
    // }
    //
    // @Test
    // public void intRunAscendingPerformanceTest() {
    // performanceTest(TestIntTimSortKernel::generateAscendingIntRuns, IntSortKernelStuff::new,
    // IntSortKernelStuff::run, getJavaComparator(), IntMergeStuff::new, IntMergeStuff::run);
    // }
    //
    @Test
    public void intRandomCorrectness() {
        for (int size = INITIAL_CORRECTNESS_SIZE; size <= MAX_CHUNK_SIZE; size *= 2) {
            correctnessTest(size, TestIntTimSortKernel::generateIntRandom, getJavaComparator(),
                    IntLongSortKernelStuff::new);
        }
    }

    @Test
    public void intAscendingRunCorrectness() {
        for (int size = INITIAL_CORRECTNESS_SIZE; size <= MAX_CHUNK_SIZE; size *= 2) {
            correctnessTest(size, TestIntTimSortKernel::generateAscendingIntRuns, getJavaComparator(),
                    IntSortKernelStuff::new);
        }
    }

    @Test
    public void intDescendingRunCorrectness() {
        for (int size = INITIAL_CORRECTNESS_SIZE; size <= MAX_CHUNK_SIZE; size *= 2) {
            correctnessTest(size, TestIntTimSortKernel::generateDescendingIntRuns, getJavaComparator(),
                    IntSortKernelStuff::new);
        }
    }

    @Test
    public void intRunCorrectness() {
        for (int size = INITIAL_CORRECTNESS_SIZE; size <= MAX_CHUNK_SIZE; size *= 2) {
            correctnessTest(size, TestIntTimSortKernel::generateIntRuns, getJavaComparator(),
                    IntSortKernelStuff::new);
        }
    }

    @Test
    public void intLongRandomCorrectness() {
        for (int size = INITIAL_CORRECTNESS_SIZE; size <= MAX_CHUNK_SIZE; size *= 2) {
            correctnessTest(size, TestIntTimSortKernel::generateIntRandom, getJavaComparator(),
                    IntLongSortKernelStuff::new);
        }
    }

    @Test
    public void intLongAscendingRunCorrectness() {
        for (int size = INITIAL_CORRECTNESS_SIZE; size <= MAX_CHUNK_SIZE; size *= 2) {
            correctnessTest(size, TestIntTimSortKernel::generateAscendingIntRuns, getJavaComparator(),
                    IntLongSortKernelStuff::new);
        }
    }

    @Test
    public void intLongDescendingRunCorrectness() {
        for (int size = INITIAL_CORRECTNESS_SIZE; size <= MAX_CHUNK_SIZE; size *= 2) {
            correctnessTest(size, TestIntTimSortKernel::generateDescendingIntRuns, getJavaComparator(),
                    IntLongSortKernelStuff::new);
        }
    }

    @Test
    public void intLongRunCorrectness() {
        for (int size = INITIAL_CORRECTNESS_SIZE; size <= MAX_CHUNK_SIZE; size *= 2) {
            correctnessTest(size, TestIntTimSortKernel::generateIntRuns, getJavaComparator(),
                    IntLongSortKernelStuff::new);
        }
    }

    @Test
    public void intRandomPartitionCorrectness() {
        for (int size = INITIAL_CORRECTNESS_SIZE; size <= MAX_PARTTITION_CHUNK_SIZE; size *= 2) {
            int partitions = 2;
            while (partitions < (int) Math.sqrt(size)) {
                partitionCorrectnessTest(size, size, partitions, TestIntTimSortKernel::generateIntRandom,
                        getJavaComparator(), IntPartitionKernelStuff::new);
                if (size < 1000) {
                    break;
                }
                partitions *= 3;
            }
        }
    }


    @Test
    public void intMultiRandomCorrectness() {
        for (int size = INITIAL_CORRECTNESS_SIZE; size <= MAX_CHUNK_SIZE; size *= 2) {
            multiCorrectnessTest(size, TestIntTimSortKernel::generateMultiIntRandom, getJavaMultiComparator(),
                    IntMultiSortKernelStuff::new);
        }
    }
}
