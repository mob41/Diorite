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

package org.diorite.inventory.item.builder;

import java.util.Collection;

import org.diorite.inventory.item.meta.BlockItemMeta;
import org.diorite.material.BlockMaterialData;

/**
 * Interface of builder of block item meta data.
 *
 * @param <B> type of builder.
 * @param <M> type of item meta.
 */
public interface IBlockItemMetaBuilder<B extends IBlockItemMetaBuilder<B, M>, M extends BlockItemMeta> extends IMetaBuilder<B, M>
{
    // TODO: finish when BlockState/BlockSnapshot will be implemented

    /**
     * Set if this tool is using CanPlaceOn tag,
     * when you set it to false, add saved materials will be removed.
     *
     * @param useCanPlaceOnTag if this tool should use CanPlaceOn tag.
     *
     * @return builder for method chains.
     */
    default B useCanPlaceOnTag(final boolean useCanPlaceOnTag)
    {
        this.meta().setUseCanPlaceOnTag(useCanPlaceOnTag);
        return this.getBuilder();
    }

    /**
     * Set if this tool is using CanPlaceOn tag,
     * when you set it to false, add saved materials will be removed.
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B useCanPlaceOnTag(final BlockItemMeta src)
    {
        this.meta().setUseCanPlaceOnTag(src.useCanPlaceOnTag());
        return this.getBuilder();
    }

    /**
     * Set materials where this block can be placed on. <br>
     * Minecraft don't support subtypes here, but diorite will save subtypes in separate tag.
     * (Vanilla client can't see them)
     *
     * @param materials new collection of materials.
     *
     * @return builder for method chains.
     */
    default B setCanPlaceOnMaterials(final Collection<BlockMaterialData> materials)
    {
        this.meta().setCanPlaceOnMaterials(materials);
        return this.getBuilder();
    }

    /**
     * Set materials where this block can be placed on. <br>
     * Minecraft don't support subtypes here, but diorite will save subtypes in separate tag.
     * (Vanilla client can't see them)
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B setCanPlaceOnMaterials(final BlockItemMeta src)
    {
        this.meta().setCanPlaceOnMaterials(src.getCanPlaceOnMaterials());
        return this.getBuilder();
    }

    /**
     * Add new material where this block can be placed on,
     * this method will automatically enable CanPlaceOn tag if needed. <br>
     * Minecraft don't support subtypes here, but diorite will save subtypes in separate tag.
     * (Vanilla client can't see them)
     *
     * @param material material to add.
     *
     * @return builder for method chains.
     */
    default B addCanPlaceOnMaterial(final BlockMaterialData material)
    {
        this.meta().addCanPlaceOnMaterial(material);
        return this.getBuilder();
    }

    /**
     * Add new material where this block can be placed on,
     * this method will automatically enable CanPlaceOn tag if needed. <br>
     * Minecraft don't support subtypes here, but diorite will save subtypes in separate tag.
     * (Vanilla client can't see them)
     *
     * @param materials material to add.
     *
     * @return builder for method chains.
     */
    default B addCanPlaceOnMaterials(final Collection<BlockMaterialData> materials)
    {
        this.meta().addCanPlaceOnMaterials(materials);
        return this.getBuilder();
    }

    /**
     * Add new material where this block can be placed on,
     * this method will automatically enable CanPlaceOn tag if needed. <br>
     * Minecraft don't support subtypes here, but diorite will save subtypes in separate tag.
     * (Vanilla client can't see them)
     *
     * @param src source item meta to copy data from it.
     *
     * @return builder for method chains.
     */
    default B addCanPlaceOnMaterials(final BlockItemMeta src)
    {
        final Collection<BlockMaterialData> mats = src.getCanPlaceOnMaterials();
        if ((mats == null) || mats.isEmpty())
        {
            return this.getBuilder();
        }
        this.meta().addCanPlaceOnMaterials(mats);
        return this.getBuilder();
    }

    /**
     * Clear list of materials where this block can be placed on,
     * this method will automatically enable CanPlaceOn tag if needed. <br>
     * Use {@link #useCanPlaceOnTag(boolean)} if you want disable CanPlaceOn tag.
     *
     * @return builder for method chains.
     */
    default B removeCanPlaceOnMaterials()
    {
        this.meta().removeCanPlaceOnMaterials();
        return this.getBuilder();
    }
}
