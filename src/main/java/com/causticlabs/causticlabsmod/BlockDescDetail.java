package com.causticlabs.causticlabsmod;

import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;

public class BlockDescDetail {

    Block block;
    int meta;

    public BlockDescDetail(String unlocalizedName, int meta) {
        this(GameData.getBlockRegistry().getRaw(unlocalizedName), meta);
    }

    public BlockDescDetail(String unlocalizedName) {
        this(GameData.getBlockRegistry().getRaw(unlocalizedName));
    }

    public BlockDescDetail(Block block) {
        this(block, -1);
    }

    public BlockDescDetail(Block block, int meta) {
        this.block = block;
        this.meta = meta;
    }
}
