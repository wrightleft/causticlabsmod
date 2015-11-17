package com.causticlabs.causticlabsmod;

import org.apache.logging.log4j.Logger;
import cofh.api.modhelpers.ThermalExpansionHelper;
import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

// Steel is only an alloy. It cannot be mined.
public class Steel {
   
   public static void apply(Logger logger) {

      // Remove all steel induction smelter recipes. We'll add our own.
      
      // Don't allow iron dust and two coal dusts. Trying both ways.
      ThermalExpansionHelper.removeSmelterRecipe(
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 2, 2));
      ThermalExpansionHelper.removeSmelterRecipe(
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 2, 2),
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1));
      
      /*
      // Don't allow iron ingot and two coal dusts.
      ThermalExpansionHelper.removeSmelterRecipe(
         ItemHelper.stack(GameData.getItemRegistry().getObject("minecraft:iron_ingot"), 1), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 2, 2));

      // Don't allow iron dust and four charcoal dusts.
      ThermalExpansionHelper.removeSmelterRecipe(
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 4, 3));
      
      // Don't allow iron ingot and four charcoal dusts.
      ThermalExpansionHelper.removeSmelterRecipe(
         ItemHelper.stack(GameData.getItemRegistry().getObject("minecraft:iron_ingot"), 1), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 4, 3));
      */
      // Add induction smelter recipes to make steel. The smelter doesn't accept
      // ore dictionary ingredients. It'll take care of the ore dictionary stuff
      // itself.
      
      /*
      // Make steel from invar dust and two coal dusts.
      ThermalExpansionHelper.addSmelterRecipe(
         8000, 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 2, 2), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 40), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("TConstruct:materials"), 1, 16));
      
      // Make steel from invar ingot and two coal dusts. It takes more energy to alloy an ingot. 
      ThermalExpansionHelper.addSmelterRecipe(
         12000, 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 2, 2), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 72), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("TConstruct:materials"), 1, 16));
      
      // Make steel from invar dust and four charcoal dusts.
      ThermalExpansionHelper.addSmelterRecipe(
         8000, 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 4, 3), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 40), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("TConstruct:materials"), 1, 16));
      
      // Make steel from invar ingot and four charcoal dusts. It takes more energy to alloy an ingot. 
      ThermalExpansionHelper.addSmelterRecipe(
         12000, 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 4, 3), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 72), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("TConstruct:materials"), 1, 16));
      
      // Make steel from invar dust and two coal. It takes more energy to alloy coal.
      ThermalExpansionHelper.addSmelterRecipe(
         14000, 
         ItemHelper.stack(GameData.getItemRegistry().getObject("minecraft:coal"), 2), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 40), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("TConstruct:materials"), 1, 16));
      
      // Make steel from invar ingot and two coal. It takes more energy to alloy an ingot or coal.
      ThermalExpansionHelper.addSmelterRecipe(
         16000, 
         ItemHelper.stack(GameData.getItemRegistry().getObject("minecraft:coal"), 2), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 72), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("TConstruct:materials"), 1, 16));
      
      // Make steel from invar dust and four charcoal. It takes more engergy to alloy charcoal.
      ThermalExpansionHelper.addSmelterRecipe(
         18000, 
         ItemHelper.stack(GameData.getItemRegistry().getObject("minecraft:coal"), 4, 1), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 40), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("TConstruct:materials"), 1, 16));
      
      // Make steel from invar ingot and four charcoal. It takes more energy to alloy an ingot or charcoal.
      ThermalExpansionHelper.addSmelterRecipe(
         20000, 
         ItemHelper.stack(GameData.getItemRegistry().getObject("minecraft:coal"), 4, 1), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("ThermalFoundation:material"), 1, 72), 
         ItemHelper.stack(GameData.getItemRegistry().getObject("TConstruct:materials"), 1, 16));
      */
   }
}
