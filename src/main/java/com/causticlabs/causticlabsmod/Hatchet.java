package com.causticlabs.causticlabsmod;

import org.apache.logging.log4j.Logger;

import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;

public class Hatchet {
   public static void apply(Logger logger) {

      // Hatchet Recipes
      
      ItemStack anyHatchetHead = CausticLabsMod.anyHatchetHead;
      
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
         anyHatchetHead, 
         "materialRod", 
         new Object[][] {{anyHatchetHead},
                         {"materialRod" }}));
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
         anyHatchetHead, 
         "materialRod", 
         new Object[][] {{null         , anyHatchetHead},
                         {"materialRod", null          }}));

      // Hatchet Head Recipes
      
      GameRegistry.addRecipe(ItemHelper.ShapedRecipe(
         PatternBuilder.instance.getToolPart(
            new ItemStack(Items.flint),
            TConstructRegistry.getItemStack("hatchetHeadPattern"), 
            null)[0],
         "##", "##",
         '#', "itemFlint"));
   }

}
