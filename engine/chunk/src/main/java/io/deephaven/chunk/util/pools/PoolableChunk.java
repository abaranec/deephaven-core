//
// Copyright (c) 2016-2025 Deephaven Data Labs and Patent Pending
//
package io.deephaven.chunk.util.pools;

import io.deephaven.chunk.Chunk;
import io.deephaven.chunk.attributes.Any;
import io.deephaven.util.SafeCloseable;

/**
 * Marker interface for {@link Chunk} subclasses that can be kept with in a {@link ChunkPool}, and whose
 * {@link #close()} method will return them to the appropriate pool.
 */
public interface PoolableChunk<ATTR extends Any> extends Chunk<ATTR>, SafeCloseable {
}
