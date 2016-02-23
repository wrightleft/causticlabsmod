package com.causticlabs.causticlabsmod.tools;

import org.apache.logging.log4j.Logger;

import com.causticlabs.causticlabsmod.CausticLabsMod;
import com.causticlabs.causticlabsmod.HarvestLevel;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe;
import com.causticlabs.causticlabsmod.ShapedUseTConToolRecipe;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe.*;

import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;
import tconstruct.tools.TinkerTools;

public class Hammer {

   public static final int CopperDamage = 100;
   public static final int CopperXP = 50;
   public static final int BrassDamage = 150;
   public static final int BrassXP = 100;
   public static final int BronzeDamage = 200;
   public static final int BronzeXP = 150;

   public static void apply(Logger logger) {

      // Hammer Recipes

      ItemStack anyHammerHead = CausticLabsMod.anyHammerHead;
      ItemStack anyToughRod = CausticLabsMod.anyToughRod;
      
      // The stone hammer is special, in that it is the starting hammer, and is needed
      // to build any copper, bronze, etc tools. It doesn't require the large plates, as
      // large plates of stone on a stone hammer are a silly idea.
      ItemStack stoneHammerHead = new ItemStack(TinkerTools.hammerHead, 1, HarvestLevel.STONE.id());
      ItemStack stoneLargePlate = new ItemStack(TinkerTools.largePlate, 1, HarvestLevel.STONE.id());
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
         new TConToolPart[][] {{new Head(anyHammerHead)},
                               {new Handle(anyToughRod)}},
         new TConToolPart[] { new Accessory(stoneLargePlate), new Extra(stoneLargePlate) }));
      
      // Hammer Head Recipes
      
      GameRegistry.addRecipe(new ShapedUseTConToolRecipe(
         new ItemStack(TinkerTools.hammerHead, 1, HarvestLevel.STONE.id()),
         Chisel.StoneDamage * 8, 
         Chisel.StoneXP * 8,
         TinkerTools.chisel, HarvestLevel.FLINT,
         new Object[][] {{"stone", "stone", "stone"}, 
                         {"stone", "stone", "stone"}, 
                         {"stone", null   , "stone"}}));
   }
}
