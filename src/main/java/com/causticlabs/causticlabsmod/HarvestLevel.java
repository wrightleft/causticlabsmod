package com.causticlabs.causticlabsmod;

import static net.minecraft.util.EnumChatFormatting.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.Logger;

import cofh.lib.util.BlockWrapper;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.library.util.HarvestLevels;
import tconstruct.tools.TinkerTools.MaterialID;

/**
 * These materials need to be defined in successive harvest levels.
 * Material names need to correspond to Tinkers' Construct materials if they
 * are going to be used as a material for tools, etc.
 * 
 * TODO - Tweak the durability and speed of the materials to make sense. To
 *        begin with, they were just copied from what Tinkers' Construct used.
 *        
 * TODO - The colors are also just copied, and may not be correct:
 *        - brass
 *        
 * TODO - This is only a smattering of all of the blocks that should
 *        be listed here.
 */
public enum HarvestLevel {   
   HAND("Hand", 
      ToolClass.SHOVEL, BlockDesc.DIRT, Blocks.sand, Blocks.glowstone),
   FLINT("Flint", getMaterialId("Flint"),
      171, 525, 2, 0.7F, 0, 0.0F, DARK_GRAY.toString(), 0x484848,
      ToolClass.PICKAXE, Blocks.coal_ore, BlockDesc.STONE, 
      ToolClass.AXE, BlockDesc.WOOD),
   STONE("Stone", getMaterialId("Stone"),
      131, 400, 1, 0.5F, 0, 1.0F, GRAY.toString(), 0x7F7F7F,
      ToolClass.PICKAXE, BlockDesc.COPPER_ORE),
   COPPER("Copper", getMaterialId("Copper"),
      180, 500, 2, 1.15F, 0, 0.0F, RED.toString(), 0xCC6410,
      ToolClass.PICKAXE, BlockDesc.ZINC_ORE),
   BRASS("Brass", 201, /* New Material */
      180, 500, 2, 1.15F, 0, 0.0F, RED.toString(), 0xCC6410,
      ToolClass.PICKAXE, BlockDesc.TIN_ORE),
   BRONZE("Bronze", getMaterialId("Bronze"),
      550, 800, 2, 1.3F, 1, 0.0F, GOLD.toString(), 0xCA9956,
      ToolClass.PICKAXE, BlockDesc.ALUMINUM_ORE),
   ALUMITE("Alumite", getMaterialId("Alumite"),
      700, 800, 3, 1.3F, 2, 0F, LIGHT_PURPLE.toString(), 0xFFA7E9,
      ToolClass.PICKAXE, BlockDesc.IRON_ORE),
   IRON("Iron", getMaterialId("Iron"),
      250, 600, 2, 1.3F, 1, 0.0F, WHITE.toString(), 0xDADADA,
      ToolClass.PICKAXE, BlockDesc.NICKEL_ORE),
   INVAR("Invar", getMaterialId("Invar"),
      250, 600, 2, 1.3F, 1, 0.0F, WHITE.toString(), 0xDADADA,
      ToolClass.PICKAXE, BlockDesc.MANGANESE_ORE),
   STEEL("Steel", getMaterialId("Steel"),
      750, 1000, 4, 1.3F, 2, 0.0F, GRAY.toString(), 0xA0A0A0,
      ToolClass.PICKAXE, BlockDesc.OBSIDIAN),
   OBSIDIAN("Obsidian", getMaterialId("Obsidian"),
      89, 700, 2, 0.8F, 3, 0.0F, LIGHT_PURPLE.toString(), 0xAA7FF5,
      ToolClass.PICKAXE, BlockDesc.SHADOW_IRON);
      
   private final String _name;
   private final int _id;
   private final ToolMaterial _material; 
   private Map<ToolClass, List<BlockWrapper>> _blocks;
   
   private HarvestLevel(
      String name,
      ToolClass toolClass,
      Object... blocks) {
      _name = name;
      _material = null;
      _id = -1;
      
      initializeBlocks(toolClass, blocks);
   }
   
   private HarvestLevel(
      String name,
      int materialID,
      int durability, 
      int speed, 
      int attack, 
      float handleModifier, 
      int reinforced, 
      float stonebound, 
      String style, 
      int primaryColor,
      ToolClass toolClass,
      Object... blocks) {

      _name = name;
      _id = materialID;
      _material = new ToolMaterial(
         name,
         ordinal(),
         durability,
         speed,
         attack,
         handleModifier,
         reinforced,
         stonebound,
         style,
         primaryColor);   
      
      if (getMaterialId(name) != materialID) {
         // If we got here, this is a new material, so we need to tell TCon 
         // about it.
         TConstructRegistry.addtoolMaterial(materialID, _material);
      }
         
      initializeBlocks(toolClass, blocks);
   }
   
   /**
    * This function will take a bunch of tool classes and blocks and create
    * a map out of them. It is basically iterating over the objects passed in
    * looking for tool classes and things that aren't tool classes. Anything 
    * that isn't a tool class will be used as a block and assigned to the last
    * tool class seen.
    * 
    * @param toolClass
    * @param objs
    */
   private void initializeBlocks(ToolClass toolClass, Object... objs) {
      // We need to start off by making the block map.
      _blocks = new HashMap<ToolClass, List<BlockWrapper>>();

      // Then starting iterating over the objects passed in.
      for (Object obj : objs) {
         if (obj instanceof ToolClass) {
            // The object is a tool class, so we must need to switch to a new
            // tool class. This doesn't require us to do anything special, just
            // set the tool class variable. The heavy lifting is done on the
            // other side of the conditional.
            toolClass = (ToolClass)obj;
         } else {
            // Get the list of blocks for this tool class. This also has the
            // side effect of creating that list if it needs to, which is the
            // case the first time the tool class is seen.
            List<BlockWrapper> existingBlocks = _blocks.get(toolClass);
            if (existingBlocks == null) {
               existingBlocks = new ArrayList<BlockWrapper>();
               _blocks.put(toolClass, existingBlocks);
            }
            
            // There are several different ways to specify a block. You can
            // pass in an actual Block reference, if you don't care about
            // meta-data. This will have the effect of applying the harvest
            // level to all of the meta-data for that block. You can also pass
            // in a BlockWrapper, which just associates a Block with some
            // meta-data.
            //
            // You can also specify BlockDesc, which are a more meta way to 
            // describe blocks. They can potentially be a list, or group, of
            // blocks.
            //
            // In addition, to support other mods, where we can't get access to 
            // the blocks directly, you can specify a string which will be used
            // to search the block registry. For example, 
            // "ThermalFoundation:Ore:1", which will translate to the copper ore
            // block.
            if (obj instanceof BlockDesc) {
               for (BlockWrapper blockWrapper : (BlockDesc)obj) {
                  existingBlocks.add(blockWrapper);  
               }
            } else {
               existingBlocks.add(Utils.getBlockWrapper(obj));
            }
         }
      }
   }

   static private int getMaterialId(String name) {
      for (Entry<Integer, ToolMaterial> entry : TConstructRegistry.toolMaterials.entrySet()) {
         if (entry.getValue().materialName == name) {
            return entry.getKey();
         }
      }
            
      return -1;
   }
   
   public int id() {
      return _id;
   }
   
   public int level() {
      return ordinal();
   }

   @Override
   public String toString() {
      return _name;
   }
   
   
   /**
    * Applying means that the material properties defined in this enumeration
    * are made effective in the game. This includes:
    * -  Setting harvest level names.
    * -  Setting the harvest level and tool classes of all of the blocks.
    * -  Setting materials and material names for Tinker's Construct.
    * 
    * @param logger
    */
   public static void apply(Logger logger) {
      
      for (HarvestLevel material : HarvestLevel.values()) {

         if (material._material != null) {
            HarvestLevels.harvestLevelNames.put(
               material._material.harvestLevel, 
               material._material.materialName);
   
            TConstructRegistry.toolMaterials.put(material._id, material._material);
            TConstructRegistry.toolMaterialStrings.put(material._material.materialName, material._material);
         }
         
         for (Entry<ToolClass, List<BlockWrapper>> entry : material._blocks.entrySet()) {
            for (BlockWrapper block : entry.getValue()) {
               // Setting the harvest level needs to done differently depending on
               // if we care about meta-data or not. If we do it without meta-data
               // then it will apply to all of the meta-data values.
               if (block.metadata >= 0) {
                  block.block.setHarvestLevel(
                     entry.getKey().toString(),
                     material.level(),
                     block.metadata);
                 logger.info(
                    String.format(
                       "Set %s:%d to harvest level %s (%d)",
                       block.block.getUnlocalizedName(),
                       block.metadata,
                       material.toString(),
                       material.level()));
               } else {
                  block.block.setHarvestLevel(
                       entry.getKey().toString(),
                       material.level());
                  logger.info(
                     String.format(
                        "Set %s to harvest level %s (%d)",
                        block.block.getUnlocalizedName(),
                        material.toString(),
                        material.level()));
               }
           }
        }
      }
   }
}
