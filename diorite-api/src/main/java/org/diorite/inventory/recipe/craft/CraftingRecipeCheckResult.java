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

import org.diorite.inventory.GridInventory;
import org.diorite.inventory.item.ItemStack;
import org.diorite.inventory.recipe.RecipeCheckResult;

/**
 * Represent results after checking if recipe is valid for given inventory.
 *
 * @see CraftingRecipe#isMatching(GridInventory)
 */
public interface CraftingRecipeCheckResult extends RecipeCheckResult
{
    /**
     * Returns matched recipe.
     *
     * @return matched recipe.
     */
    @Override
    CraftingRecipe getRecipe();

    /**
     * Returns result itemstack.
     *
     * @return result itemstack.
     */
    ItemStack getResult();

    /**
     * Returns crafting grid of items that should be removed from inventory on craft. <br>
     * Grid should match crafting slots.
     *
     * @return crafting grid of items that should be removed from inventory on craft.
     */
    CraftingGrid getItemsToConsume();
}
