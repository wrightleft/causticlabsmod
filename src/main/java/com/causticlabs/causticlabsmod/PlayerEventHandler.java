package com.causticlabs.causticlabsmod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerEventHandler {
    @SubscribeEvent
    public void onEvent(PlayerEvent.BreakSpeed event) {
        if(event.entityPlayer == null) {
            return;
        }

        if(event.block == null || event.block == Blocks.air) {
            return;
        }

        int blockHarvestLevel = event.block.getHarvestLevel(event.metadata);

        if (blockHarvestLevel <= 0) {
            return;
        }

        String tool = event.block.getHarvestTool(event.metadata);
        ItemStack currentItem = event.entityPlayer.getCurrentEquippedItem();

        if (currentItem != null && currentItem.getItem() != null) {
            if (currentItem.getItem().getHarvestLevel(currentItem, tool) >= blockHarvestLevel) {
                return;
            }
        }

        event.setCanceled(true);
    }
}
