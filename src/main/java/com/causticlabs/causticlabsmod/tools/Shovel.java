package com.causticlabs.causticlabsmod.tools;

import org.apache.logging.log4j.Logger;

import com.causticlabs.causticlabsmod.CausticLabsMod;
import com.causticlabs.causticlabsmod.HarvestLevel;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe.Handle;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe.Head;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe.TConToolPart;

import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import tconstruct.tools.TinkerTools;

public class Shovel {

   public static void postInit(Logger logger) {
      logger.info("Shovel - Post Initialization");

      // Shovel Recipes
      
      ItemStack anyShovelHead = CausticLabsMod.anyShovelHead;

      GameRegistry.addRecipe(new ShapedTConToolRecipe(
         new TConToolPart[][] {{new Head(anyShovelHead)},
                               {new Handle("materialRod")}}));
      
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
         new TConToolPart[][] {{null                     , new Head(anyShovelHead)},
                               {new Handle("materialRod"), null                   }}));

      // Shovel Head Recipes

      GameRegistry.addRecipe(ItemHelper.ShapedRecipe(
         new ItemStack(TinkerTools.shovelHead, 1, HarvestLevel.FLINT.id()),
         " # ", 
         "###", 
         "# #",
         '#', "itemFlint"));
   }
}
