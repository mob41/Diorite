/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Diorite (by Bartłomiej Mazur (aka GotoFinal))
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

package org.diorite.impl.inventory.item.meta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.diorite.inventory.item.enchantments.Enchantment;
import org.diorite.inventory.item.meta.HideFlag;
import org.diorite.inventory.item.meta.ItemMeta;
import org.diorite.nbt.NbtTag;
import org.diorite.nbt.NbtTagCompound;
import org.diorite.nbt.NbtTagList;
import org.diorite.nbt.NbtTagType;

import gnu.trove.TCollections;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.hash.TObjectShortHashMap;

@SuppressWarnings({"unchecked", "rawtypes"})
public class SimpleItemMetaImpl extends ItemMetaImpl
{
    private static final String SEP               = ".";
    private static final String DISPLAY           = "display";
    private static final String DISPLAY_NAME      = DISPLAY + SEP + "Name";
    private static final String LORE              = "Lore";
    private static final String DISPLAY_LORE      = DISPLAY + SEP + LORE;
    private static final String HIDE_FLAGS        = "HideFlags";
    private static final String ENCHANTMENTS      = "ench";
    private static final String ENCHANTMENT_ID    = "id";
    private static final String ENCHANTMENT_LEVEL = "lvl";

    public SimpleItemMetaImpl(final NbtTagCompound tag)
    {
        super(tag);
    }

    @Override
    public boolean hasDisplayName()
    {
        return this.tag.containsTag(DISPLAY_NAME);
    }

    @Override
    public String getDisplayName()
    {
        return this.tag.getString(DISPLAY_NAME);
    }

    @Override
    public void setDisplayName(final String name)
    {
        if (name == null)
        {
            this.tag.removeTag(DISPLAY_NAME);
            return;
        }
        this.tag.setString(DISPLAY_NAME, name);
    }

    @Override
    public boolean hasLore()
    {
        return this.tag.containsTag(DISPLAY_LORE);
    }

    @Override
    public List<String> getLore()
    {
        final NbtTag tag = this.tag.getTag(DISPLAY_LORE, NbtTagList.class);
        if (tag == null)
        {
            return null;
        }
        return new ArrayList<>((Collection) tag.getNBTValue());
    }

    @Override
    public void setLore(final List<String> lore)
    {
        if (lore == null)
        {
            this.tag.removeTag(DISPLAY_LORE);
            return;
        }
        this.tag.addTag(DISPLAY, new NbtTagList(LORE, NbtTagType.STRING, lore));
    }

    @Override
    public boolean hasEnchants()
    {
        return this.tag.getList(ENCHANTMENTS, NbtTagCompound.class, new ArrayList<>(1)).isEmpty();
    }

    @Override
    public boolean hasEnchant(final Enchantment enchantment)
    {
        final List<NbtTagCompound> tags = this.tag.getList(ENCHANTMENTS, NbtTagCompound.class);
        if (tags == null)
        {
            return false;
        }
        for (final NbtTagCompound tag : tags)
        {
            if (tag.getShort(ENCHANTMENT_ID) == enchantment.getID())
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getEnchantLevel(final Enchantment enchantment)
    {
        final List<NbtTagCompound> tags = this.tag.getList(ENCHANTMENTS, NbtTagCompound.class);
        if (tags == null)
        {
            return 0;
        }
        for (final NbtTagCompound tag : tags)
        {
            if (tag.getShort(ENCHANTMENT_ID) == enchantment.getID())
            {
                return tag.getShort(ENCHANTMENT_LEVEL);
            }
        }
        return 0;
    }

    @Override
    public TObjectShortMap<Enchantment> getEnchants()
    {
        final List<NbtTagCompound> tags = this.tag.getList(ENCHANTMENTS, NbtTagCompound.class);
        if (tags == null)
        {
            return TCollections.unmodifiableMap(new TObjectShortHashMap<>(1, 1, (short) - 1));
        }
        final TObjectShortMap<Enchantment> result = new TObjectShortHashMap<>(tags.size(), 1, (short) - 1);
        for (final NbtTagCompound tag : tags)
        {
            final Enchantment ench = null; // TODO getByID(tsg.getShort(ENCHANTMENT_ID));
            result.put(ench, tag.getShort(ENCHANTMENT_LEVEL));
        }
        return TCollections.unmodifiableMap(result);
    }

    @Override
    public boolean addEnchant(final Enchantment enchantment, final int level, final boolean ignoreLevelRestriction)
    {
        if ((level <= 0) || (! ignoreLevelRestriction && (level > enchantment.getMaxLevel())))
        {
            return false;
        }
        NbtTagList list = this.tag.getTag(ENCHANTMENTS, NbtTagList.class);
        if (list == null)
        {
            list = new NbtTagList(ENCHANTMENTS, 4);
            this.tag.addTag(list);
        }
        else
        {
            for (final NbtTagCompound nbt : list.getTags(NbtTagCompound.class))
            {
                if (nbt.getShort(ENCHANTMENT_ID) == enchantment.getID())
                {
                    if (nbt.getShort(ENCHANTMENT_LEVEL) == level)
                    {
                        return false;
                    }
                    nbt.setShort(ENCHANTMENT_LEVEL, level);
                    return true;
                }
            }
        }
        final NbtTagCompound ench = new NbtTagCompound();
        ench.setShort(ENCHANTMENT_ID, enchantment.getID());
        ench.setShort(ENCHANTMENT_LEVEL, level);
        list.addTag(ench);
        return true;
    }

    @Override
    public boolean removeEnchant(final Enchantment enchantment)
    {
        final NbtTagList list = this.tag.getTag(ENCHANTMENTS, NbtTagList.class);
        for (final Iterator<NbtTag> it = list.iterator(); it.hasNext(); )
        {
            final NbtTagCompound nbt = (NbtTagCompound) it.next();
            if (nbt.getShort(ENCHANTMENT_ID) == enchantment.getID())
            {
                it.remove();
                if (list.isEmpty())
                {
                    this.tag.removeTag(ENCHANTMENTS);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasConflictingEnchant(final Enchantment enchantment)
    {
        final List<NbtTagCompound> list = this.tag.getList(ENCHANTMENTS, NbtTagCompound.class);
        for (final NbtTagCompound nbt : list)
        {
            final Enchantment ench = null; // TODO getByID(nbt.getShort(ENCHANTMENT_ID));
            if (enchantment.conflictsWith(ench))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeEnchantments()
    {
        this.tag.removeTag(ENCHANTMENTS);
    }

    @Override
    public void addHideFlags(final HideFlag... hideFlags)
    {
        int flags = this.tag.getInt(HIDE_FLAGS);
        final Set<HideFlag> set = HideFlag.getFlags(flags);
        Collections.addAll(set, hideFlags);
        flags = HideFlag.join(set);
        if (flags == 0)
        {
            this.tag.removeTag(HIDE_FLAGS);
        }
        this.tag.setInt(HIDE_FLAGS, flags);
    }

    @Override
    public void removeHideFlags(final HideFlag... hideFlags)
    {
        int flags = this.tag.getInt(HIDE_FLAGS);
        final Set<HideFlag> set = HideFlag.getFlags(flags);
        for (final HideFlag hideFlag : hideFlags)
        {
            set.remove(hideFlag);
        }
        flags = HideFlag.join(set);
        if (flags == 0)
        {
            this.tag.removeTag(HIDE_FLAGS);
        }
        this.tag.setInt(HIDE_FLAGS, flags);
    }

    @Override
    public Set<HideFlag> getHideFlags()
    {
        return HideFlag.getFlags(this.tag.getInt(HIDE_FLAGS));
    }

    @Override
    public boolean hasHideFlag(final HideFlag flag)
    {
        return HideFlag.getFlags(this.tag.getInt(HIDE_FLAGS)).contains(flag);
    }

    @Override
    public void removeHideFlags()
    {
        this.tag.removeTag(HIDE_FLAGS);
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    public ItemMeta clone()
    {
        return new SimpleItemMetaImpl(this.tag.clone());
    }
}