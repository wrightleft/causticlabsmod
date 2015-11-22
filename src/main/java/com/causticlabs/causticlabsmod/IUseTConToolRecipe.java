package com.causticlabs.causticlabsmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.IRecipe;

public interface IUseTConToolRecipe extends IRecipe {
	public void damageTools(InventoryCrafting inventoryCrafting, EntityPlayer player);
	public boolean matches(InventoryCrafting inventoryCrafting);
}
