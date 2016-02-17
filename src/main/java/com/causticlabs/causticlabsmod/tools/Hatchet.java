package com.causticlabs.causticlabsmod.tools;

import org.apache.logging.log4j.Logger;

import com.causticlabs.causticlabsmod.CausticLabsMod;
import com.causticlabs.causticlabsmod.HarvestLevel;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe.*;

import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.tools.TinkerTools;

public class Hatchet {
   public static void apply(Logger logger) {

      // Hatchet Recipes
      
      ItemStack anyHatchetHead = CausticLabsMod.anyHatchetHead;
      
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
         new TConToolPart[][] {{new Head(anyHatchetHead) },
                               {new Handle("materialRod")}}));
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
         new TConToolPart[][] {{null                     , new Head(anyHatchetHead)},
                               {new Handle("materialRod"), null                    }}));

      // Hatchet Head Recipes
      
      GameRegistry.addRecipe(ItemHelper.ShapedRecipe(
         new ItemStack(TinkerTools.hatchetHead, 1, HarvestLevel.FLINT.id()),
         "##", "##",
         '#', "itemFlint"));
   }
}
