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

public class Invar {
   public static void apply(Logger logger) {
      logger.info("applying invar customizations");
      
      Smeltery.getAlloyList().removeIf(
         alloy -> 
            (alloy.result != null) && 
            (FluidHelper.isFluidEqual(TinkerSmeltery.moltenInvarFluid, alloy.result)));
      
      Smeltery.addAlloyMixing(
         new FluidStack(TinkerSmeltery.moltenInvarFluid, TConstruct.nuggetLiquidValue * 3),
         new FluidStack(TinkerSmeltery.moltenIronFluid, TConstruct.nuggetLiquidValue * 2),
         new FluidStack(TinkerSmeltery.moltenNickelFluid, TConstruct.nuggetLiquidValue * 1));
   }
}
