/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016. Diorite (by Bartłomiej Mazur (aka GotoFinal))
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.diorite.event.chunk;

import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.event.Event;
import org.diorite.world.World;
import org.diorite.world.chunk.ChunkPos;

/**
 * Base class for chunk related events.
 */
public class ChunkEvent extends Event
{
    protected final ChunkPos chunkPos;

    /**
     * Construct new world event.
     *
     * @param chunkPos world related to event, can't be null, and must contains world.
     */
    public ChunkEvent(final ChunkPos chunkPos)
    {
        Validate.notNull(chunkPos, "chunk position can't be null.");
        Validate.notNull(chunkPos.getWorld(), "world of chunk position can't be null.");
        this.chunkPos = chunkPos;
    }

    /**
     * @return world related to event.
     *
     * @see ChunkPos#getWorld()
     */
    public World getWorld()
    {
        return this.chunkPos.getWorld();
    }

    /**
     * @return chunk position related to event.
     */
    public ChunkPos getChunkPos()
    {
        return this.chunkPos;
    }

    /**
     * @return chunk X position related to event.
     */
    public int getChunkX()
    {
        return this.chunkPos.getX();
    }

    /**
     * @return chunk Z position related to event.
     */
    public int getChunkZ()
    {
        return this.chunkPos.getZ();
    }

    @Override
    public boolean equals(final Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (! (o instanceof ChunkEvent))
        {
            return false;
        }
        if (! super.equals(o))
        {
            return false;
        }

        final ChunkEvent that = (ChunkEvent) o;

        return this.chunkPos.equals(that.chunkPos);

    }

    @Override
    public int hashCode()
    {
        int result = super.hashCode();
        result = (31 * result) + this.chunkPos.hashCode();
        return result;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("chunkPos", this.chunkPos).toString();
    }
}
