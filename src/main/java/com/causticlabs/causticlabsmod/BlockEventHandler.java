package com.causticlabs.causticlabsmod;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;

public class BlockEventHandler {
    @SubscribeEvent
    public void onEvent(BlockEvent.HarvestDropsEvent event) {

        // Sometimes, leaves should drop sticks. This is the only to get
        // sticks at the beginning of the game.
        if (event.block.equals(Blocks.leaves) ||
            event.block.equals(Blocks.leaves2)) {
            if (event.world.rand.nextInt(100) < 10) {
                event.drops.add(new ItemStack(Items.stick, 1));
                event.dropChance = 1.0f;
            }
        }
    }
}
