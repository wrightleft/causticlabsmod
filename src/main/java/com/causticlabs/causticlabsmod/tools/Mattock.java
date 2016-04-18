package com.causticlabs.causticlabsmod.tools;

import org.apache.logging.log4j.Logger;

import com.causticlabs.causticlabsmod.CausticLabsMod;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe.Accessory;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe.Handle;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe.Head;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe.TConToolPart;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class Mattock {

   public static void postInit(Logger logger) {
      logger.info("Mattock - Post Initialization");

      // Hatchet Recipes
      
      ItemStack anyHatchetHead = CausticLabsMod.anyHatchetHead;
      ItemStack anyShovelHead = CausticLabsMod.anyShovelHead;
      
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
         new TConToolPart[][] {{new Accessory(anyShovelHead), null                     , new Head(anyHatchetHead)},
                               {null                        , new Handle("materialRod"), null                    }}));
   }
}
