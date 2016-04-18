package com.causticlabs.causticlabsmod;

import org.apache.logging.log4j.Logger;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.tools.TinkerTools;

public class OreDict {

   public static void postInit(Logger logger) {
      logger.info("OreDictionary: postInit");
      
      // Fix the ore dictionary for TCon in a couple of places to make the
      // recipes easier.
      
      // Make any kind of tool rod available as a generic rod.
      OreDictionary.registerOre("materialRod", new ItemStack(TinkerTools.toolRod, 1, OreDictionary.WILDCARD_VALUE));
      
      // Make every stick available as a handle.
      for (ItemStack itemStack : OreDictionary.getOres("stickWood")) {
         OreDictionary.registerOre("materialRod", itemStack);
      }

      // Make every binding available as a generic binding.
      OreDictionary.registerOre("materialBinding", new ItemStack(TinkerTools.binding, 1, OreDictionary.WILDCARD_VALUE));
      
      // Make every kind of string available as a binding.
      OreDictionary.registerOre("materialBinding", new ItemStack(Items.string, 1, OreDictionary.WILDCARD_VALUE));
   }
}
