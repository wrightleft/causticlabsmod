package com.causticlabs.causticlabsmod;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

public interface IUseTConToolRecipe extends IRecipe {
	public void damageTools(InventoryCrafting inventoryCrafting, EntityPlayer player);
	public boolean matches(InventoryCrafting inventoryCrafting);
}
