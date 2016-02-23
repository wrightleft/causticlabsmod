package com.causticlabs.causticlabsmod.materials;

import org.apache.logging.log4j.Logger;

import cofh.api.modhelpers.ThermalExpansionHelper;
import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.ItemStack;

/**
 * Steel is only an alloy. It cannot be mined.
 * 
 * TODO - This is all wrong. Adjust accordingly.
 */
public class Steel {
   
   public static void apply(Logger logger) {
      
      logger.info("Applying steel customizations");

      // Add induction smelter recipes to make steel. The smelter doesn't accept
      // ore dictionary ingredients. It'll take care of the ore dictionary stuff
      // itself.
      
      ItemStack steel = ItemHelper.stack(GameData.getItemRegistry().getObject("TConstruct:materials"), 1, 16);
      ItemStack coalDust = ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 2);
      ItemStack invarDust = ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 40);
      ItemStack invarIngot = ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 72);
      ItemStack charcoalDust = ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 3);
      ItemStack coal = ItemHelper.stack(GameData.getItemRegistry().getObject("minecraft:coal"), 1, 0);
      ItemStack charcoal = ItemHelper.stack(GameData.getItemRegistry().getObject("minecraft:coal"), 1, 1);
      
      // Make steel from invar dust and two coal dusts.
      ThermalExpansionHelper.addSmelterRecipe(
         8000, 
         ItemHelper.cloneStack(coalDust, 2), 
         ItemHelper.cloneStack(invarDust, 1), 
         ItemHelper.cloneStack(steel, 1));
      
      // Make steel from invar ingot and two coal dusts. It takes more energy to alloy an ingot. 
      ThermalExpansionHelper.addSmelterRecipe(
         12000, 
         ItemHelper.cloneStack(coalDust, 2), 
         ItemHelper.cloneStack(invarIngot, 1), 
         ItemHelper.cloneStack(steel, 1));
      
      // Make steel from invar dust and four charcoal dusts.
      ThermalExpansionHelper.addSmelterRecipe(
         8000, 
         ItemHelper.cloneStack(charcoalDust, 4), 
         ItemHelper.cloneStack(invarDust, 1), 
         ItemHelper.cloneStack(steel, 1));
      
      // Make steel from invar ingot and four charcoal dusts. It takes more energy to alloy an ingot. 
      ThermalExpansionHelper.addSmelterRecipe(
         12000, 
         ItemHelper.cloneStack(charcoalDust, 4), 
         ItemHelper.cloneStack(invarIngot, 1), 
         ItemHelper.cloneStack(steel, 1));
      
      // Make steel from invar dust and two coal. It takes more energy to alloy coal.
      ThermalExpansionHelper.addSmelterRecipe(
         14000, 
         ItemHelper.cloneStack(coal, 2), 
         ItemHelper.cloneStack(invarDust, 1), 
         ItemHelper.cloneStack(steel, 1));
      
      // Make steel from invar ingot and two coal. It takes more energy to alloy an ingot or coal.
      ThermalExpansionHelper.addSmelterRecipe(
         16000, 
         ItemHelper.cloneStack(coal, 2), 
         ItemHelper.cloneStack(invarIngot, 1), 
         ItemHelper.cloneStack(steel, 1));
      
      // Make steel from invar dust and four charcoal. It takes more engergy to alloy charcoal.
      ThermalExpansionHelper.addSmelterRecipe(
         18000, 
         ItemHelper.cloneStack(charcoal, 4), 
         ItemHelper.cloneStack(invarDust, 1), 
         ItemHelper.cloneStack(steel, 1));
      
      // Make steel from invar ingot and four charcoal. It takes more energy to alloy an ingot or charcoal.
      ThermalExpansionHelper.addSmelterRecipe(
         20000, 
         ItemHelper.cloneStack(charcoal, 4), 
         ItemHelper.cloneStack(invarIngot, 1), 
         ItemHelper.cloneStack(steel, 1));
   }
}
