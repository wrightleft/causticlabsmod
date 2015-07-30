package com.causticlabs.causticlabsmod;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tconstruct.library.crafting.ToolBuilder;

import java.util.List;

public class ShapedTConToolRecipe extends ShapedOreRecipe {

    static {
        RecipeSorter.register(
            CausticLabsMod.ID + ":ShapedTConToolRecipe",
            ShapedTConToolRecipe.class,
            RecipeSorter.Category.SHAPED, "");
    }

    private Object head = null;
    private Object handle = null;
    private Object accessory = null;
    private Object extra = null;

    private Object translateIngredient(Object object) {
        if (object instanceof ItemStack) {
            return ((ItemStack) object).copy();
        } else if (object instanceof Item) {
            return new ItemStack((Item) object);
        } else if (object instanceof String) {
            return OreDictionary.getOres((String) object);
        } else {
            throw new RuntimeException("invalid ingredient");
        }
    }

    public ShapedTConToolRecipe(String shape_1, String shape_2, Object head, Object handle) {
        // The apple is just there because the super class needs something, or
        // it won't compile.
        super(Items.apple, shape_1, shape_2, 'A', head, 'B', handle);

        this.head = translateIngredient(head);
        this.handle = translateIngredient(handle);
    }

    public ShapedTConToolRecipe(String shape_1, String shape_2, Object head, Object handle, Object accessory) {
        // The apple is just there because the super class needs something, or
        // it won't compile.
        super(Items.apple, shape_1, shape_2, 'A', head, 'B', handle, 'C', accessory);

        this.head = translateIngredient(head);
        this.handle = translateIngredient(handle);
        this.accessory = translateIngredient(accessory);
    }

    public ShapedTConToolRecipe(String shape_1, String shape_2, String shape_3, Object head, Object handle, Object accessory) {
        super(Items.apple, shape_1, shape_2, shape_3, 'A', head, 'B', handle, 'C', accessory);

        this.head = translateIngredient(head);
        this.handle = translateIngredient(handle);
        this.accessory = translateIngredient(accessory);
    }

    private boolean matches(ItemStack providedIngredient, Object object) {
        if (object instanceof ItemStack) {
            ItemStack ingredient = (ItemStack)object;
            return ingredient.getItem() == providedIngredient.getItem();
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
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        ItemStack providedHead = null;
        ItemStack providedHandle = null;
        ItemStack providedAccessory = null;
        ItemStack providedExtra = null;

        for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
            ItemStack providedIngredient = inventoryCrafting.getStackInSlot(i);
            if (providedIngredient != null) {
                if ((providedHead == null) && matches(providedIngredient, head)) {
                    providedHead = providedIngredient;
                } else if ((providedHandle == null) && matches(providedIngredient, handle)) {
                    providedHandle = providedIngredient;
                } else if ((accessory != null) && (providedAccessory == null) && matches(providedIngredient, accessory)) {
                    providedAccessory = providedIngredient;
                } else if ((extra != null) && (providedExtra == null) && matches(providedExtra, extra)) {
                    providedExtra = providedIngredient;
                }
            }
        }

        if (providedExtra != null) {
            return ToolBuilder.instance.buildTool(providedHead, providedHandle, providedAccessory, providedExtra, null);
        } else if (providedAccessory != null) {
            return ToolBuilder.instance.buildTool(providedHead, providedHandle, providedAccessory, null, null);
        } else {
            return ToolBuilder.instance.buildTool(providedHead, providedHandle, null, null, null);
        }
    }

    @Override
    public ItemStack getRecipeOutput() {
        return null;
    }
}
