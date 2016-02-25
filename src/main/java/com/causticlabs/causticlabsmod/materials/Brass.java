package com.causticlabs.causticlabsmod.materials;

import org.apache.logging.log4j.Logger;

import com.causticlabs.causticlabsmod.Utils;

import cofh.lib.util.BlockWrapper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import tconstruct.TConstruct;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.Smeltery;

public class Brass {
   
   static Fluid FLUID;
   static BlockWrapper BLOCK;
   
   public static void init(Logger logger) {
      logger.info("Brass: init");
      
      FLUID = FluidRegistry.getFluid("brass.molten");
      BLOCK = Utils.getBlockWrapper("Metallurgy:precious.block:3");
   }

   public static void postInit(Logger logger) {
      logger.info("Brass: postInit");
      
      Smeltery.addDictionaryMelting(
         "ingotBrass", 
         new FluidType(BLOCK.block, BLOCK.metadata, 500, FLUID, true), 
         0, 
         TConstruct.ingotLiquidValue);
   }
}
