package com.causticlabs.causticlabsmod;

import iguanaman.iguanatweakstconstruct.replacing.ModPartReplacement;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.RecipeSorter;
import tconstruct.library.crafting.ModifyBuilder;
import tconstruct.library.modifier.ItemModifier;
import tconstruct.library.tools.ToolCore;

import java.util.ArrayList;

public class ShapelessTConModRecipe implements IRecipe{
    private ItemModifier modifier = null;

    static {
        RecipeSorter.register(
            CausticLabsMod.ID + ":ShapelessTConModRecipe",
            ShapelessTConModRecipe.class,
            RecipeSorter.Category.SHAPELESS, "");
    }

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        ItemStack tool = null;
        ItemStack[] providedIngredients = new ItemStack[inventoryCrafting.getSizeInventory()];

        for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
            ItemStack providedIngredient = inventoryCrafting.getStackInSlot(i);
            if (providedIngredient != null) {
                if ((tool == null) && (providedIngredient.getItem() instanceof ToolCore)) {
                    tool = providedIngredient;
                } else {
                    providedIngredients[i] = providedIngredient;
                }
            }
        }

        if(tool != null) {
            for (ItemModifier modifier : ModifyBuilder.instance.itemModifiers) {
                if (modifier.matches(providedIngredients, tool))
                {
                    this.modifier = modifier;
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        ItemStack tool = null;
        ArrayList<ItemStack> providedIngredients = new ArrayList<>();

        for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
            ItemStack providedIngredient = inventoryCrafting.getStackInSlot(i);
            if (providedIngredient != null) {
                if (providedIngredient.getItem() instanceof ToolCore) {
                    tool = providedIngredient;
                } else {
                    providedIngredients.add(providedIngredient);
                }
            }
        }

        ItemStack modifiedTool = tool.copy();
        modifier.modify(providedIngredients.toArray(new ItemStack[0]), modifiedTool);
        return modifiedTool;
    }

    @Override
    public int getRecipeSize() {
        return 2;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
