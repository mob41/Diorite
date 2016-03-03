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

package org.diorite.material.block;

import java.util.Collection;

import org.diorite.BlockLocation;
import org.diorite.entity.Entity;
import org.diorite.material.AnyType;
import org.diorite.material.block.state.BlockState;
import org.diorite.material.block.state.BlockStateEntry;
import org.diorite.material.data.drops.PossibleDrops;
import org.diorite.material.item.ItemType;

/**
 * Represent block data
 */
public interface BlockType extends AnyType
{
    /**
     * Returns numeric id used (not always, sometimes {@link #getMinecraftId()} is used) in save files, packets, etc.
     *
     * @return numeric id used (not always, sometimes {@link #getMinecraftId()} is used) in save files, packets, etc.
     *
     * @see #getProxyId()
     */
    @Override
    int getId();

    /**
     * Returns id used in packets, used for adding fake blocks.
     *
     * @return id used in packets, used for adding fake blocks.
     */
    @Override
    default int getProxyId()
    {
        return this.getId();
    }

    /**
     * Returns minecraft string id of block.
     *
     * @return minecraft string id of block.
     */
    @Override
    String getMinecraftId();

    /**
     * Returns minecraft string id of block used in packets, used for adding fake blocks.
     *
     * @return minecraft string id of block used in packets, used for adding fake blocks.
     */
    @Override
    default String getProxyMinecraftId()
    {
        return this.getMinecraftId();
    }

    /**
     * Returns display name of block.
     *
     * @return display name of block.
     */
    @Override
    String getDisplayNameKey();

    /**
     * Returns subtype by given id.
     *
     * @param id id of subtype.
     *
     * @return subtype by given id.
     */
    @Override
    BlockSubtype getSubtype(int id);

    /**
     * Returns subtype by given id.
     *
     * @param id id of subtype.
     *
     * @return subtype by given id.
     */
    @Override
    BlockSubtype getSubtype(String id);

    /**
     * Get subtype of block by BlockState and value of it. <br>
     * If state isn't supported by this object, or value isn't in valid range, {@link #getDefaultSubtype()} will be returned.
     *
     * @param state state of block.
     * @param value value of block state.
     * @param <T>   type of BlockState value.
     *
     * @return some subtype of block.
     */
    default <T> BlockSubtype getSubtype(BlockState<T> state, T value)
    {
        return this.getSubtype(new BlockStateEntry<>(state, value));
    }

    /**
     * Get subtype of block by BlockState and value of it. <br>
     * If state isn't supported by this object, or value isn't in valid range, {@link #getDefaultSubtype()} will be returned.
     *
     * @param entires block states of subtype.
     * @param <T>     type of BlockState value.
     *
     * @return some subtype of block.
     */
    BlockSubtype getSubtype(BlockStateEntry<?>... entires);

    /**
     * Adds new subtype to this block.
     *
     * @param subtype new subtype of block.
     */
    void addSubtype(BlockSubtype subtype);

    /**
     * Returns collection of subtypes for this block.
     *
     * @return collection of subtypes for this block.
     */
    @Override
    Collection<? extends BlockSubtype> getSubtypes();

    /**
     * Returns default subtype of this block type.
     *
     * @return default subtype of this block type.
     */
    BlockSubtype getDefaultSubtype();

    /**
     * Set default subtype of this block type.
     *
     * @param defaultSubtype new default subtype of this block.
     */
    void setDefaultSubtype(BlockSubtype defaultSubtype);

    /**
     * Returns default subtype of block.
     *
     * @return default subtype of block.
     */
    @Override
    BlockSubtype asSubtype();

    /**
     * Returns hardness of block.
     *
     * @return hardness of block.
     */
    double getHardness();

    /**
     * Returns hardness of block.
     *
     * @return hardness of block.
     */
    double getBlastResistance();

    /**
     * Returns true if this block is solid.
     *
     * @return true if this block is solid.
     */
    boolean isSolid();

    /**
     * Returns sounds pack for this block.
     *
     * @return sounds pack for this block.
     */
    BlockSounds getSounds();

    /**
     * Returns true if given entity is colliding with block at given position.
     *
     * @param blockLocation location of block.
     * @param entity        entity to be checked.
     *
     * @return true if given entity is colliding with block at given position.
     */
    default boolean isCollidingWith(final BlockLocation blockLocation, final Entity entity)
    {
        return this.isCollidingWith(blockLocation.getX(), blockLocation.getY(), blockLocation.getZ(), entity);
    }

    /**
     * Returns true if given entity is colliding with block at given position.
     *
     * @param x      x coordinates of block.
     * @param y      y coordinates of block.
     * @param z      z coordinates of block.
     * @param entity entity to be checked.
     *
     * @return true if given entity is colliding with block at given position.
     */
    boolean isCollidingWith(int x, int y, int z, Entity entity);

    /**
     * Returns true if this block is tile entity.
     *
     * @return true if this block is tile entity.
     */
    boolean isTileEntity();

    /**
     * Returns item for this block if exists.
     *
     * @return item for this block if exists.
     */
    ItemType getItem();

    /**
     * Returns possible drops of this block.
     *
     * @return possible drops of this block.
     */
    PossibleDrops getDrops();

    /**
     * Retruns liquid related block parameters.
     *
     * @return liquid related block parameters.
     */
    LiquidSettings getLiquidSettings();

    /**
     * Override block {@link LiquidSettings}.
     *
     * @param settings new parameters to use.
     */
    void setLiquidSettings(LiquidSettings settings);

    /**
     * Retruns light related block parameters.
     *
     * @return light related block parameters.
     */
    LightSettings getLightSettings();

    /**
     * Override block {@link LightSettings}.
     *
     * @param settings new parameters to use.
     */
    void setLightSettings(LightSettings settings);

    /**
     * Retruns fire related block parameters.
     *
     * @return fire related block parameters.
     */
    FlameableSettings getFlameableSettings();

    /**
     * Override block {@link FlameableSettings}.
     *
     * @param settings new parameters to use.
     */
    void setFlameableSettings(FlameableSettings settings);

    /**
     * Returns interact handler.
     *
     * @return interact handler.
     */
    BlockInteractHandler getInteractHandler();
}