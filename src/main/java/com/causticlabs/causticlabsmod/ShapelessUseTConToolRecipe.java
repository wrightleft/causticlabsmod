package com.causticlabs.causticlabsmod;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import tconstruct.library.tools.HarvestTool;
import tconstruct.library.tools.ToolCore;

import javax.tools.Tool;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShapelessUseTConToolRecipe implements IRecipe {
    private final ItemStack result;
    private final int damage;
    private final List<Object> ingredients = new ArrayList<>();

    static {
        RecipeSorter.register(
            CausticLabsMod.ID + ":ShapelessUseTConToolRecipe",
            ShapelessUseTConToolRecipe.class,
            RecipeSorter.Category.SHAPELESS, "");
    }

    public ShapelessUseTConToolRecipe(ItemStack result, int damage, Object... ingredients) {
        this.result = result.copy();
        this.damage = damage;

        // Build our list of ingredients by converting whatever object we got
        // into an ItemStack.
        for (Object ingredient : ingredients) {
            if (ingredient instanceof ItemStack) {
                this.ingredients.add(((ItemStack)ingredient).copy());
            } else if (ingredient instanceof Item) {
                this.ingredients.add(new ItemStack((Item)ingredient));
            } else if (ingredient instanceof Block) {
                this.ingredients.add(new ItemStack((Block)ingredient));
            } else if (ingredient instanceof String) {
                this.ingredients.add(OreDictionary.getOres((String)ingredient));
            } else {
                throw new RuntimeException("invalid ingredient");
            }
        }
    }

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        return matches(inventoryCrafting);
    }

    public boolean matches(IInventory inventoryCrafting) {
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
        return ingredients.size();
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }

    public void damageTools(IInventory inventoryCrafting, EntityPlayer player) {
        for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
            ItemStack providedIngredient = inventoryCrafting.getStackInSlot(i);
            if (providedIngredient != null) {
                if (providedIngredient.getItem() instanceof ToolCore) {
                    tconstruct.library.tools.AbilityHelper.damageTool(
                        providedIngredient,
                        damage,
                        player,
                        false);
                    ++providedIngredient.stackSize;
                }
            }
        }
    }
}
