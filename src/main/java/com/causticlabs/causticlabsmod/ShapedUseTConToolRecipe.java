package com.causticlabs.causticlabsmod;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tconstruct.library.tools.ToolCore;

import java.util.ArrayList;
import java.util.List;

public class ShapedUseTConToolRecipe implements IRecipe {

    static {
        RecipeSorter.register(
            CausticLabsMod.ID + ":ShapedUseTConToolRecipe",
            ShapedUseTConToolRecipe.class,
            RecipeSorter.Category.SHAPED, "");
    }

    private final List<Object> ingredients = new ArrayList<>();
    private final ItemStack result;
    private final int damage;
    private int width = 0;
    private int height = 0;

    private Object translateIngredient(Object object) {
        if (object instanceof ItemStack) {
            return ((ItemStack) object).copy();
        } else if (object instanceof Item) {
            return new ItemStack((Item) object);
        } else if (object instanceof Block) {
            return new ItemStack((Block)object);
        } else if (object instanceof String) {
            return OreDictionary.getOres((String) object);
        } else {
            throw new RuntimeException("invalid ingredient");
        }
    }

    public ShapedUseTConToolRecipe(ItemStack result, int damage, Object[][] recipe) {
        this.result = result;
        this.damage = damage;
        height = recipe.length;

        // The rows may be different sizes, as Java doesn't prevent that. So use
        // the largest size.
        for (Object[] row : recipe) {
            width = Math.max(width, row.length);
        }

        // Convert the recipe double array into a single array. This'll make it
        // easier to compare against later.
        for (Object[] row : recipe) {
            for (int i = 0; i < this.width; ++i) {
                if (i < row.length) {
                    this.ingredients.add(row[i]);
                } else {
                    this.ingredients.add(null);
                }
            }
        }
    }

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        return matches(inventoryCrafting);
    }

    public boolean matches(IInventory inventoryCrafting) {

        for (int x = 0; x <= 3 - width; x++) {
            for (int y = 0; y <= 3 - height; ++y) {

            }
        }

        ArrayList<Object> unmatchedIngredients = new ArrayList<>(ingredients);
        for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
            ItemStack providedIngredient = inventoryCrafting.getStackInSlot(i);
            if (providedIngredient != null) {
                if (!unmatchedIngredients.removeIf(
                    (Object object) -> {
                        if (object instanceof ItemStack) {
                            ItemStack ingredient = (ItemStack)object;
                            if (ingredient.getItem() == providedIngredient.getItem()) {
                                if (ingredient.getItem() instanceof ToolCore) {
                                    // The needed ingredient is a TCon tool, then
                                    // all we need to do is to make sure the
                                    // provided one isn't broken.
                                    return !providedIngredient.getTagCompound().getCompoundTag("InfiTool").getBoolean("Broken");
                                } else {
                                    // The item we're trying to match isn't the
                                    // tool. So we can apply the normal rules of
                                    // matching, which is pretty much just based on
                                    // damage.
                                    //
                                    // Either we allow any damage, or we need to
                                    // match a specific damage.
                                    return
                                        ingredient.getItemDamage() == OreDictionary.WILDCARD_VALUE ||
                                            ingredient.getItemDamage() == providedIngredient.getItemDamage();
                                }
                            } else {
                                // The needed ingredient didn't match the provided
                                // one.
                                return false;
                            }
                        } else if (object instanceof List) {
                            List<ItemStack> ingredients = (List<ItemStack>)object;
                            for (ItemStack ingredient : ingredients) {
                                if (OreDictionary.itemMatches(ingredient, providedIngredient, false)) {
                                    return true;
                                }
                            }

                            return false;
                        } else {
                            return false;
                        }
                    }))
                {
                    // If we couldn't remove anything from the list of needed
                    // ingredients, then we must not match.
                    return false;
                }
            } else {
                // The spot in the crafting inventory was empty. Move on to the
                // next one.
            }
        }

        return unmatchedIngredients.isEmpty();
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        return result.copy();
    }

    @Override
    public int getRecipeSize() {
        return 0;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
