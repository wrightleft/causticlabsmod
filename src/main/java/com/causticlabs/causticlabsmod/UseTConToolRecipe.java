package com.causticlabs.causticlabsmod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.tools.ToolCore;
import tconstruct.tools.TinkerTools;

public abstract class UseTConToolRecipe implements IUseTConToolRecipe {

    private final ItemStack result;
    private final int damage;
    
    UseTConToolRecipe(ItemStack result, int damage) {
    	this.result = result;
    	this.damage = damage;
    }
    		
    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
        return result.copy();
    }

	@Override
	public ItemStack getRecipeOutput() {
		return null;
	}

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting, World world) {
        return matches(inventoryCrafting);
    }

	@Override
	public void damageTools(InventoryCrafting inventoryCrafting, EntityPlayer player) {
        for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
            ItemStack providedIngredient = inventoryCrafting.getStackInSlot(i);
            if (providedIngredient != null) {
                if (providedIngredient.getItem() instanceof ToolCore) {
                    tconstruct.library.tools.AbilityHelper.damageTool(
                        providedIngredient,
                        damage,
                        player,
                        false);

            		// The chisel already handles not consuming itself during crafting.
                    // None of the other tools do though.
                	if (providedIngredient.getItem() != TinkerTools.chisel) {
                        ++providedIngredient.stackSize;
                	}
                }
            }
        }
    }

}
