//
// Copyright (c) 2016-2024 Deephaven Data Labs and Patent Pending
//
package io.deephaven.engine.table.impl.select.analyzers;

import io.deephaven.base.log.LogOutput;
import io.deephaven.engine.liveness.LivenessNode;
import io.deephaven.engine.table.TableUpdate;
import io.deephaven.engine.table.ModifiedColumnSet;
import io.deephaven.engine.table.ColumnSource;
import io.deephaven.engine.rowset.RowSet;
import io.deephaven.engine.table.impl.util.JobScheduler;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BaseLayer extends SelectAndViewAnalyzer {
    private final Map<String, ColumnSource<?>> sources;
    private final boolean publishTheseSources;

    BaseLayer(Map<String, ColumnSource<?>> sources, boolean publishTheseSources) {
        super(BASE_LAYER_INDEX);
        this.sources = sources;
        this.publishTheseSources = publishTheseSources;
    }

    @Override
    int getLayerIndexFor(String column) {
        if (sources.containsKey(column)) {
            return BASE_LAYER_INDEX;
        }
        throw new IllegalArgumentException("Unknown column: " + column);
    }

    @Override
    void setBaseBits(BitSet bitset) {
        bitset.set(BASE_LAYER_INDEX);
    }

    @Override
    public void setAllNewColumns(BitSet bitset) {
        bitset.set(BASE_LAYER_INDEX);
    }

    @Override
    void populateModifiedColumnSetRecurse(ModifiedColumnSet mcsBuilder, Set<String> remainingDepsToSatisfy) {
        mcsBuilder.setAll(remainingDepsToSatisfy.toArray(String[]::new));
    }

    @Override
    final Map<String, ColumnSource<?>> getColumnSourcesRecurse(GetMode mode) {
        // We specifically return a LinkedHashMap so the columns get populated in order
        final Map<String, ColumnSource<?>> result = new LinkedHashMap<>();
        if (mode == GetMode.All || (mode == GetMode.Published && publishTheseSources)) {
            result.putAll(sources);
        }
        return result;
    }

    @Override
    public void applyUpdate(TableUpdate upstream, RowSet toClear, UpdateHelper helper, JobScheduler jobScheduler,
            @Nullable LivenessNode liveResultOwner, SelectLayerCompletionHandler onCompletion) {
        // nothing to do at the base layer
        onCompletion.onLayerCompleted(BASE_LAYER_INDEX);
    }

    @Override
    final Map<String, Set<String>> calcDependsOnRecurse(boolean forcePublishAllSources) {
        final Map<String, Set<String>> result = new HashMap<>();
        if (publishTheseSources || forcePublishAllSources) {
            for (final String col : sources.keySet()) {
                result.computeIfAbsent(col, dummy -> new HashSet<>()).add(col);
            }
        }
        return result;
    }

    @Override
    public SelectAndViewAnalyzer getInner() {
        return null;
    }

    @Override
    public void startTrackingPrev() {
        // nothing to do
    }

    @Override
    public LogOutput append(LogOutput logOutput) {
        return logOutput.append("{BaseLayer").append(", layerIndex=").append(getLayerIndex()).append("}");
    }

    @Override
    public boolean allowCrossColumnParallelization() {
        return true;
    }
}
