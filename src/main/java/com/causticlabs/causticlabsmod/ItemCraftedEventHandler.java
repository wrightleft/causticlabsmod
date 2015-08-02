package com.causticlabs.causticlabsmod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.crafting.CraftingManager;

public class ItemCraftedEventHandler {

    @SubscribeEvent
    public void onPlayerItemCrafted(PlayerEvent.ItemCraftedEvent event) {
    	if (event.craftMatrix instanceof InventoryCrafting) {
    		InventoryCrafting inventoryCrafting = (InventoryCrafting)event.craftMatrix;
	        for (Object recipe : CraftingManager.getInstance().getRecipeList()) {
	            if (recipe instanceof IUseTConToolRecipe) {
	            	IUseTConToolRecipe useTConToolRecipe = (IUseTConToolRecipe)recipe;
	                if (useTConToolRecipe.matches(inventoryCrafting)) {
	                	useTConToolRecipe.damageTools(inventoryCrafting, event.player);
	                	break;
	                }
	            }
	        }
    	}
    }
}
