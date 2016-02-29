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

package org.diorite.inventory.recipe.craft;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import org.diorite.entity.Player;
import org.diorite.inventory.item.ItemStack;

/**
 * Represent simple recipe item, that check name/lore/etc only if recipe item have custom one,
 * and may replace to other item after crafting.
 */
public class SimpleCraftingRecipeItem implements CraftingRecipeItem
{
    /**
     * Pattern item of this recipe.
     */
    protected final ItemStack                                   item;
    /**
     * If pattern should ignore material sub-type.
     */
    protected final boolean                                     ignoreData;
    /**
     * Validators if this recipe item, allowing to check more item data. <br>
     * Player may be null when using.
     */
    protected final Collection<BiPredicate<Player, ItemStack>>  validators;
    /**
     * Basic Replacment item.
     *
     * @see #getReplacement()
     */
    protected final ItemStack                                   replacement;
    /**
     * Replacment function.
     *
     * @see #getReplacement(Player, CraftingGrid)
     */
    protected final BiFunction<Player, CraftingGrid, ItemStack> replacementFunc;

    /**
     * Construct new recipe item with given item as pattern.
     *
     * @param item            pattern item.
     * @param ignoreData      if pattern item should ignore subtype of material
     * @param replacementFunc replacement item function, see {@link #getReplacement(Player, CraftingGrid)}, may be null.
     * @param replacement     replacement item, see {@link #getReplacement()}
     * @param validators      validators of thic recipe item, allowing to check additional data of item. Player may be null.
     */
    public SimpleCraftingRecipeItem(final ItemStack item, final boolean ignoreData, final BiFunction<Player, CraftingGrid, ItemStack> replacementFunc, final ItemStack replacement, final Collection<BiPredicate<Player, ItemStack>> validators)
    {
        this.item = item.clone();
        this.ignoreData = ignoreData;
        this.validators = (validators == null) ? null : new ArrayList<>(validators);
        this.replacement = replacement;
        this.replacementFunc = replacementFunc;
    }

    /**
     * Construct new recipe item with given item as pattern.
     *
     * @param item       pattern item.
     * @param ignoreData if pattern item should ignore subtype of material
     * @param validators validators of thic recipe item, allowing to check additional data of item. Player may be null.
     */
    public SimpleCraftingRecipeItem(final ItemStack item, final boolean ignoreData, final Collection<BiPredicate<Player, ItemStack>> validators)
    {
        this.item = item.clone();
        this.ignoreData = ignoreData;
        this.validators = (validators == null) ? null : new ArrayList<>(validators);
        this.replacement = null;
        this.replacementFunc = null;
    }

    @Override
    public ItemStack getReplacement(final Player player, final CraftingGrid grid)
    {
        return (this.replacementFunc == null) ? this.replacement : this.replacementFunc.apply(player, grid).clone();
    }

    @Override
    public ItemStack isValid(final Player player, final ItemStack item)
    {
        if (item == null)
        {
            return null;
        }
        final boolean valid = (this.ignoreData ? this.item.getMaterial().equals(item.getMaterial()) : this.item.getMaterial().equals(item.getMaterial())) && (this.item.getAmount() <= item.getAmount());
        if (! valid)
        {
            return null;
        }
        if (! MultiCraftingRecipeItem.fuzzyEquals(this.item.getItemMeta().getRawNbtData(), item.getItemMeta().getRawNbtData()))
        {
            return null;
        }
        if ((this.validators != null) && ! this.validators.stream().allMatch(f -> f.test(player, item)))
        {
            return null;
        }
        final ItemStack is = item.clone();
        is.setAmount(this.item.getAmount());
        return is;
    }

    @Override
    public ItemStack getItem()
    {
        return this.item.clone();
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString()).append("item", this.item).append("ignoreData", this.ignoreData).append("validators", this.validators).append("replacement", this.replacement).append("replacementFunc", this.replacementFunc).toString();
    }
}
