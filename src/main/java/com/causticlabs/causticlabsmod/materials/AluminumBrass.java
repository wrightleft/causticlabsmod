package com.causticlabs.causticlabsmod.materials;

import org.apache.logging.log4j.Logger;

import cofh.lib.util.helpers.FluidHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import scala.actors.threadpool.Arrays;
import tconstruct.TConstruct;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.Smeltery;
import tconstruct.smeltery.TinkerSmeltery;

public class AluminumBrass {
   public static void apply(Logger logger) {
      logger.info("applying aluminum brass customizations");
      
      Smeltery.getAlloyList().removeIf(
         alloy -> 
            (alloy.result != null) && 
            (FluidHelper.isFluidEqual(TinkerSmeltery.moltenAlubrassFluid, alloy.result)));
      
      Smeltery.addAlloyMixing(
         new FluidStack(TinkerSmeltery.moltenAlubrassFluid, TConstruct.nuggetLiquidValue * 3),
         new FluidStack(TinkerSmeltery.moltenAluminumFluid, TConstruct.nuggetLiquidValue * 2),
         new FluidStack(Brass.FLUID, TConstruct.nuggetLiquidValue * 1));
   }
}
