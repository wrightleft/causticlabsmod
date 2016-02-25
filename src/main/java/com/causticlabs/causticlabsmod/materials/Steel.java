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
   
   public static void postInit(Logger logger) {
      
      logger.info("Applying steel customizations");

      // Add induction smelter recipes to make steel. The smelter doesn't accept
      // ore dictionary ingredients. It'll take care of the ore dictionary stuff
      // itself.

      ItemStack ironIngot = ItemHelper.stack(GameData.getItemRegistry().getObject("minecraft:iron_ingot"), 1, 0);
      ItemStack ironDust = ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 0);

      ItemStack manganeseIngot = ItemHelper.stack(GameData.getItemRegistry().getObject("Metallurgy:manganese.ingot"), 1, 0);
      ItemStack manganeseDust = ItemHelper.stack(GameData.getItemRegistry().getObject("Metallurgy:base.dust"), 1, 2);

      ItemStack steelIngot = ItemHelper.stack(GameData.getItemRegistry().getObject("TConstruct:materials"), 1, 16);
      
      ThermalExpansionHelper.addSmelterRecipe(
         8000, 
         ItemHelper.cloneStack(manganeseDust, 1), 
         ItemHelper.cloneStack(ironDust, 2), 
         ItemHelper.cloneStack(steelIngot, 2));
      
      ThermalExpansionHelper.addSmelterRecipe(
         10000, 
         ItemHelper.cloneStack(manganeseDust, 1), 
         ItemHelper.cloneStack(ironIngot, 2), 
         ItemHelper.cloneStack(steelIngot, 2));
      
      ThermalExpansionHelper.addSmelterRecipe(
         10000, 
         ItemHelper.cloneStack(manganeseIngot, 1), 
         ItemHelper.cloneStack(ironDust, 2), 
         ItemHelper.cloneStack(steelIngot, 2));
      
      ThermalExpansionHelper.addSmelterRecipe(
         12000, 
         ItemHelper.cloneStack(manganeseIngot, 1), 
         ItemHelper.cloneStack(ironIngot, 2), 
         ItemHelper.cloneStack(steelIngot, 2));
   }
}
