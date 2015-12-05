package com.causticlabs.causticlabsmod;

import static net.minecraft.util.EnumChatFormatting.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.apache.logging.log4j.Logger;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.crafting.ToolBuilder;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.library.util.HarvestLevels;
import tconstruct.tools.TinkerTools;

public enum Material {
   Hand("shovel", BlockDesc.DIRT),
   Flint(171, 525, 2, 0.7F, 0, 0.0F, DARK_GRAY.toString(), 0x484848,
      "pickaxe", BlockDesc.COAL_ORE, BlockDesc.STONE,
      "shovel", BlockDesc.SAND,
      "axe", BlockDesc.WOOD);
   
   private final int _id;
   private final ToolMaterial _material; 
   private Map<String, List<BlockDesc>> _blocks;
   
   private Material(
      String toolClass,
      Object... blocks) {
      _id = -1;
      _material = null;
      
      initializeBlocks(toolClass, blocks);
   }
   
   private Material(
      int durability, 
      int speed, 
      int attack, 
      float handleModifier, 
      int reinforced, 
      float stonebound, 
      String style, 
      int primaryColor,
      String toolClass,
      Object... blocks) {
      
      _id = getMaterialId(name());
      _material = new ToolMaterial(
         name(),
         ordinal(),
         durability,
         speed,
         attack,
         handleModifier,
         reinforced,
         stonebound,
         style,
         primaryColor);      

      initializeBlocks(toolClass, blocks);
   }
   
   private void initializeBlocks(String toolClass, Object... blocks) {
      _blocks = new HashMap<String, List<BlockDesc>>();

      for (Object block : blocks) {
         if (block instanceof String) {
            toolClass = (String)block;
         } else {
            List<BlockDesc> existingBlocks = _blocks.get(toolClass);
            if (existingBlocks == null) {
               existingBlocks = new ArrayList<BlockDesc>();
               _blocks.put(toolClass, existingBlocks);
            }
            existingBlocks.add((BlockDesc)block);
         }
      }
   }

   private int getMaterialId(String name) {
      for (Entry<Integer, ToolMaterial> entry : TConstructRegistry.toolMaterials.entrySet()) {
         if (entry.getValue().materialName == name) {
            return entry.getKey();
         }
      }
      
      throw new RuntimeException("invalid tool material");
   }
   
   public int id() {
      return _id;
   }
   
   public int level() {
      return ordinal();
   }
   
   public static void apply(Logger logger) {
      
      for (Material material : Material.values()) {

         if (material._material != null) {
            HarvestLevels.harvestLevelNames.put(
               material._material.harvestLevel, 
               material._material.materialName);
   
            TConstructRegistry.toolMaterials.put(material._id, material._material);
            TConstructRegistry.toolMaterialStrings.put(material._material.materialName, material._material);
         }
         
         for (Entry<String, List<BlockDesc>> entry : material._blocks.entrySet()) {
            for (BlockDesc blockDesc : entry.getValue()) {
               for (BlockDescDetail blockDescDetail : blockDesc) {
                  // Setting the harvest level needs to done differently depending on
                  // if we care about meta-data or not. If we do it without meta-data
                  // then it will apply to all of the meta-data values.
                  if (blockDescDetail.meta >= 0) {
                      blockDescDetail.block.setHarvestLevel(
                          entry.getKey(),
                          material.level(),
                          blockDescDetail.meta);
                      logger.info(
                          String.format(
                              "Set %s:%d to harvest level %s (%d)",
                              blockDescDetail.block.getUnlocalizedName(),
                              blockDescDetail.meta,
                              material.name(),
                              material.level()));
                  } else {
                      blockDescDetail.block.setHarvestLevel(
                          entry.getKey(),
                          material.level());
                      logger.info(
                          String.format(
                              "Set %s:* to harvest level %s (%d)",
                              blockDescDetail.block.getUnlocalizedName(),
                              material.name(),
                              material.level()));
                  }
              }
           }
        }
      }
   }
}
