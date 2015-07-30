package com.causticlabs.causticlabsmod;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;
import tconstruct.library.util.HarvestLevels;
import tconstruct.world.TinkerWorld;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum HarvestLevel {
    HAND(0, "Hand", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.GLOWSTONE).collect(Collectors.toList())),
        new SimpleEntry<>("shovel", Stream.of(
            BlockDesc.DIRT).collect(Collectors.toList()))).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()))),
    WOOD(1, "Wood", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.COAL_ORE,
            BlockDesc.STONE).collect(Collectors.toList())),
        new SimpleEntry<>("shovel", Stream.of(
            BlockDesc.SAND).collect(Collectors.toList())),
        new SimpleEntry<>("axe", Stream.of(
            BlockDesc.WOOD).collect(Collectors.toList()))).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()))),
    STONE(2, "Stone", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.TIN_ORE,
            BlockDesc.COPPER_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    BRONZE(3, "Bronze", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.IRON_ORE,
            BlockDesc.ALUMINUM_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    IRON(4, "Iron", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.NICKEL_ORE,
            BlockDesc.LAPIS_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    INVAR(5, "Invar", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.LEAD_ORE,
            BlockDesc.REDSTONE_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    STEEL(6, "Steel", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.SILVER_ORE,
            BlockDesc.OBSIDIAN).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    OBSIDIAN(7, "Obsidian", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.GOLD_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    DARK_STEEL(8, "Dark Steel", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.PLATINUM_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    ALUMITE(9, "Alumite", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.ARDITE_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    ARDITE(10, "Ardite", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.MITHRIL_ORE,
            BlockDesc.COBALT_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    COBALT(11, "Cobalt", new HashMap<String, List<BlockDesc>>()),
    MANYULLYN(12, "Manyullyn", new HashMap<String, List<BlockDesc>>());

    private final int level;
    private final String name;
    private final Map<String, List<BlockDesc>> map;

    HarvestLevel(
            int level,
            String name,
            Map<String, List<BlockDesc>> map) {
        this.level = level;
        this.name = name;
        this.map = map;
    }

    public static void apply(Logger logger) {
        // A few vanilla harvest levels get overridden in the static
        // initialization of this class. Best to get it out of the way first.
        new ForgeHooks();

        for (HarvestLevel harvestLevel : HarvestLevel.values()) {
            // Set harvest level names. These really can only be set in
            // TConstruct's map of names. TConstruct will use them, and WAILA.
            HarvestLevels.harvestLevelNames.put(harvestLevel.level, harvestLevel.name);

            for (Entry<String, List<BlockDesc>> entry : harvestLevel.map.entrySet()) {
                for (BlockDesc blockDesc : entry.getValue()) {
                    for (BlockDescDetail blockDescDetail : blockDesc) {
                        if (blockDescDetail.meta >= 0) {
                            blockDescDetail.block.setHarvestLevel(
                                entry.getKey(),
                                harvestLevel.level,
                                blockDescDetail.meta);
                            logger.info(
                                String.format(
                                    "Set %s:%d to harvest level %s (%d)",
                                    blockDescDetail.block.getUnlocalizedName(),
                                    blockDescDetail.meta,
                                    harvestLevel.name,
                                    harvestLevel.level));
                        } else {
                            blockDescDetail.block.setHarvestLevel(
                                entry.getKey(),
                                harvestLevel.level);
                            logger.info(
                                String.format(
                                    "Set %s:* to harvest level %s (%d)",
                                    blockDescDetail.block.getUnlocalizedName(),
                                    harvestLevel.name,
                                    harvestLevel.level));
                        }
                    }
                }
            }
        }
    }
}
