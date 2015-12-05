package com.causticlabs.causticlabsmod;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.ForgeHooks;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.library.util.HarvestLevels;

public enum HarvestLevel {
    HAND(0, "Hand", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.GLOWSTONE).collect(Collectors.toList())),
        new SimpleEntry<>("shovel", Stream.of(
            BlockDesc.DIRT).collect(Collectors.toList()))).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()))),
    FLINT(1, "Flint", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.COAL_ORE,
            BlockDesc.STONE).collect(Collectors.toList())),
        new SimpleEntry<>("shovel", Stream.of(
            BlockDesc.SAND).collect(Collectors.toList())),
        new SimpleEntry<>("axe", Stream.of(
            BlockDesc.WOOD).collect(Collectors.toList()))).collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()))),
    STONE(2, "Stone", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.COPPER_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    COPPER(3, "Copper", Stream.of(
       new SimpleEntry<>("pickaxe", Stream.of(
           BlockDesc.ZINC_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    BRASS(4, "Brass", Stream.of(
       new SimpleEntry<>("pickaxe", Stream.of(
           BlockDesc.TIN_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    BRONZE(5, "Bronze", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.IRON_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    IRON(6, "Iron", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.ALUMINUM_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    ALUMITE(7, "Alumite", Stream.of(
       new SimpleEntry<>("pickaxe", Stream.of(
           BlockDesc.NICKEL_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    INVAR(8, "Invar", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.MANGANESE_ORE).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    STEEL(9, "Steel", Stream.of(
        new SimpleEntry<>("pickaxe", Stream.of(
            BlockDesc.OBSIDIAN).collect(Collectors.toList()))).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()))),
    OBSIDIAN(10, "Obsidian", new HashMap<String, List<BlockDesc>>()),
    SHADOW_STEEL(11, "Shadow Steel", new HashMap<String, List<BlockDesc>>()),
    VYROXERES(12, "Vyroxeres", new HashMap<String, List<BlockDesc>>()),
    KALENDRITE(13, "Kalendrite", new HashMap<String, List<BlockDesc>>()),
    ARDITE(14, "Ardite", new HashMap<String, List<BlockDesc>>()),
    COBALT(15, "Cobalt", new HashMap<String, List<BlockDesc>>()),
    VULCANITE(16, "Vulcanite", new HashMap<String, List<BlockDesc>>()),
    SANGUINITE(17, "Sanguinite", new HashMap<String, List<BlockDesc>>());

    public final int level;
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
    
    private static int getMaterialId(String name) {
       for (Entry<Integer, ToolMaterial> entry : TConstructRegistry.toolMaterials.entrySet()) {
          if (entry.getValue().materialName == name) {
             return entry.getKey();
          }
       }
       
       throw new RuntimeException("invalid tool material");
    }

    public static void apply(Logger logger) {
        // A few vanilla harvest levels get overridden in the static
        // initialization of this class. Best to get it out of the way first.
        new ForgeHooks();

        for (HarvestLevel harvestLevel : HarvestLevel.values()) {
            // Set harvest level names. These really can only be set in
            // TConstruct's map of names. TConstruct will use them, and WAILA.
            HarvestLevels.harvestLevelNames.put(harvestLevel.level, harvestLevel.name);

            // For some reason the TCon materials are pretty much unmodifiable. Therefore,
            // we need to create new materials. Oh, and there is no way to remove a tool
            // material, so we'll also need to do an end run around the normal way of
            // adding a material, and add it manually. Make sure this corresponds with
            // TConstructRegistry.addToolMaterial() roughly.            
            ToolMaterial oldMaterial = TConstructRegistry.getMaterial(harvestLevel.name);
            
            if (oldMaterial != null) {
               ToolMaterial toolMaterial = new ToolMaterial(
                  oldMaterial.materialName, 
                  oldMaterial.localizationString, 
                  harvestLevel.level, 
                  oldMaterial.durability, 
                  oldMaterial.miningspeed, 
                  oldMaterial.attack, 
                  oldMaterial.handleModifier, 
                  oldMaterial.reinforced, 
                  oldMaterial.stonebound, 
                  oldMaterial.tipStyle, 
                  oldMaterial.primaryColor);
               
               TConstructRegistry.toolMaterials.put(getMaterialId(harvestLevel.name), toolMaterial);
               TConstructRegistry.toolMaterialStrings.put(harvestLevel.name, toolMaterial);
            }

            for (Entry<String, List<BlockDesc>> entry : harvestLevel.map.entrySet()) {
               for (BlockDesc blockDesc : entry.getValue()) {
                  for (BlockDescDetail blockDescDetail : blockDesc) {
                     // Setting the harvest level needs to done differently depending on
                     // if we care about meta-data or not. If we do it without meta-data
                     // then it will apply to all of the meta-data values.
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
