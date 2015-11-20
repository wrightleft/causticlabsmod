package com.causticlabs.causticlabsmod;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class Chisel {
   public static void apply(Logger logger) {

      // Chisel Recipes
      
      ItemStack anyChiselHead = CausticLabsMod.anyChiselHead;
      
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyChiselHead,
            "materialRod",
            new Object[][] {{anyChiselHead},
                            {"materialRod"}}));
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyChiselHead,
            "materialRod",
            new Object[][] {{null         , anyChiselHead},
                            {"materialRod", null         }}));
   }
}
