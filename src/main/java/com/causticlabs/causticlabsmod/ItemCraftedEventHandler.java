package com.causticlabs.causticlabsmod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.item.crafting.CraftingManager;

public class ItemCraftedEventHandler {

    @SubscribeEvent
    public void onPlayerItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        for (Object recipe : CraftingManager.getInstance().getRecipeList()) {
            if (recipe instanceof ShapelessUseTConToolRecipe) {
                ShapelessUseTConToolRecipe shapelessUseTConToolRecipe = (ShapelessUseTConToolRecipe)recipe;
                if (shapelessUseTConToolRecipe.matches(event.craftMatrix)) {
                    shapelessUseTConToolRecipe.damageTools(event.craftMatrix, event.player);
                }
            }
        }
    }
}
