package com.causticlabs.causticlabsmod.materials;

import org.apache.logging.log4j.Logger;

import com.causticlabs.causticlabsmod.BlockDesc;
import com.causticlabs.causticlabsmod.Utils;

import cofh.lib.util.BlockWrapper;
import cofh.lib.util.helpers.FluidHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import scala.actors.threadpool.Arrays;
import tconstruct.TConstruct;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.FluidType;
import tconstruct.library.crafting.Smeltery;
import tconstruct.smeltery.TinkerSmeltery;

public class Brass {
   
   static Fluid FLUID;
   static BlockWrapper BLOCK;
   
   public static void init(Logger logger) {
      logger.info("Brass: init");
      
      FLUID = FluidRegistry.getFluid("brass.molten");
      BLOCK = Utils.getBlockWrapper("Metallurgy:precious.block:3");
   }

   public static void postInit(Logger logger) {
      logger.info("Brasss: postInit");
      
      Smeltery.addDictionaryMelting(
         "ingotBrass", 
         new FluidType(BLOCK.block, BLOCK.metadata, 500, FLUID, true), 
         0, 
         TConstruct.ingotLiquidValue);
   }
}
