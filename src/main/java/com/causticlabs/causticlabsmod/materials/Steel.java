package com.causticlabs.causticlabsmod.materials;

import org.apache.logging.log4j.Logger;

import cofh.api.modhelpers.ThermalExpansionHelper;
import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.ItemStack;

/**
 * Steel is an alloy that can only be made in the induction smelter.
 * The smeltery just doesn't cut it.
 */
public class Steel {
   
   public static void postInit(Logger logger) {
      
      logger.info("Applying steel customizations");

      // Add induction smelter recipes to make steel. The smelter doesn't accept
      // ore dictionary ingredients. It'll take care of the ore dictionary stuff
      // itself.

      ItemStack ironDust = ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 0);
      ItemStack ironIngot = ItemHelper.stack(GameData.getItemRegistry().getObject("minecraft:iron_ingot"), 1, 0);

      ItemStack obsidianBlock = ItemHelper.stack(GameData.getItemRegistry().getObject("minecraft:obsidian"), 1, 0);
      ItemStack obsidianIngot = ItemHelper.stack(GameData.getItemRegistry().getObject("TConstruct:materials"), 1, 18);
      ItemStack obsidianDust = ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 4);

      ItemStack steelIngot = ItemHelper.stack(GameData.getItemRegistry().getObject("TConstruct:materials"), 1, 16);
      
      ThermalExpansionHelper.addSmelterRecipe(
         8000, 
         ItemHelper.cloneStack(obsidianDust, 1), 
         ItemHelper.cloneStack(ironDust, 2), 
         ItemHelper.cloneStack(steelIngot, 2));
      
      ThermalExpansionHelper.addSmelterRecipe(
         10000, 
         ItemHelper.cloneStack(obsidianIngot, 1), 
         ItemHelper.cloneStack(ironDust, 2), 
         ItemHelper.cloneStack(steelIngot, 2));
      
      ThermalExpansionHelper.addSmelterRecipe(
         48000, 
         ItemHelper.cloneStack(obsidianBlock, 1), 
         ItemHelper.cloneStack(ironDust, 8), 
         ItemHelper.cloneStack(steelIngot, 8));
      
      

      ThermalExpansionHelper.addSmelterRecipe(
         10000, 
         ItemHelper.cloneStack(obsidianDust, 1), 
         ItemHelper.cloneStack(ironIngot, 2), 
         ItemHelper.cloneStack(steelIngot, 2));
      
      ThermalExpansionHelper.addSmelterRecipe(
         12000, 
         ItemHelper.cloneStack(obsidianIngot, 1), 
         ItemHelper.cloneStack(ironIngot, 2), 
         ItemHelper.cloneStack(steelIngot, 2));
      
      ThermalExpansionHelper.addSmelterRecipe(
         56000, 
         ItemHelper.cloneStack(obsidianBlock, 1), 
         ItemHelper.cloneStack(ironIngot, 8), 
         ItemHelper.cloneStack(steelIngot, 8));
   }
}
