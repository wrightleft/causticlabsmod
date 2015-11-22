package com.causticlabs.causticlabsmod;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.init.Blocks;
import tconstruct.world.TinkerWorld;

// We don't need to worry about the dense ores, as they take on the harvest
// level of their counter part automatically.
public enum BlockDesc implements Iterable<BlockDescDetail>{
    STONE(Stream.of(
        new BlockDescDetail(Blocks.stone),
        new BlockDescDetail(Blocks.sandstone),
        new BlockDescDetail(Blocks.stonebrick),
        new BlockDescDetail(Blocks.cobblestone),
        new BlockDescDetail(Blocks.mossy_cobblestone),
        new BlockDescDetail(Blocks.sandstone)).collect(Collectors.toList())),
    SAND(new BlockDescDetail(Blocks.sand)),
    COAL_ORE(new BlockDescDetail(Blocks.coal_ore)),
    IRON_ORE(new BlockDescDetail(Blocks.iron_ore)),
    ALUMINUM_ORE(new BlockDescDetail(TinkerWorld.oreSlag, 5)),
    REDSTONE_ORE(Stream.of(
       new BlockDescDetail(Blocks.redstone_ore),
       new BlockDescDetail(Blocks.lit_redstone_ore)).collect(Collectors.toList())),
    LAPIS_ORE("minecraft:lapis_ore"),
    GOLD_ORE("minecraft:gold_ore"),
    OBSIDIAN("minecraft:obsidian"),
    GLOWSTONE("minecraft:glowstone"),
    TIN_ORE("ThermalFoundation:Ore", 0),
    COPPER_ORE("ThermalFoundation:Ore", 1),
    SILVER_ORE("ThermalFoundation:Ore", 2),
    LEAD_ORE("ThermalFoundation:Ore", 3),
    NICKEL_ORE("ThermalFoundation:Ore", 4),
    PLATINUM_ORE("ThermalFoundation:Ore", 5),
    MITHRIL_ORE("ThermalFoundation:Ore", 6),
    ARDITE_ORE(new BlockDescDetail(TinkerWorld.oreSlag, 2)),
    COBALT_ORE(new BlockDescDetail(TinkerWorld.oreSlag, 1)),

    NETHER_QUARTZ_ORE("minecraft:quartz_ore"),
    DIAMOND_ORE("minecraft:diamond_ore"),
    EMERALD_ORE("minecraft:emerald_ore"),

    WOOD(Stream.of(
        new BlockDescDetail(Blocks.log),
        new BlockDescDetail(Blocks.log2),
        new BlockDescDetail(Blocks.planks)).collect(Collectors.toList())),
    DIRT(new BlockDescDetail(Blocks.dirt));

    @Override
    public Iterator<BlockDescDetail> iterator() {
        return blockDescDetails.iterator();
    }

    BlockDesc(String unlocalizedName) {
        this(unlocalizedName, -1);
    }

    BlockDesc(String unlocalizedName, int meta) {
        this(new BlockDescDetail(unlocalizedName, meta));
    }

    BlockDesc(BlockDescDetail blockDescDetail) {
        this(Stream.of(blockDescDetail).collect(Collectors.toList()));
    }

    BlockDesc(List<BlockDescDetail> blockDescDetails) {
        this.blockDescDetails = blockDescDetails;
    }

    private List<BlockDescDetail> blockDescDetails;
}
