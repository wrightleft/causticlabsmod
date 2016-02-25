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

public class Alumite {
   public static void postInit(Logger logger) {
      logger.info("applying alumite customizations");
      
      Smeltery.getAlloyList().removeIf(
         alloy -> 
            (alloy.result != null) && 
            (FluidHelper.isFluidEqual(TinkerSmeltery.moltenAlumiteFluid, alloy.result)));
      
      Smeltery.addAlloyMixing(
         new FluidStack(TinkerSmeltery.moltenAlumiteFluid, TConstruct.nuggetLiquidValue * 3),
         new FluidStack(TinkerSmeltery.moltenAluminumFluid, TConstruct.nuggetLiquidValue * 2),
         new FluidStack(TinkerSmeltery.moltenTinFluid, TConstruct.nuggetLiquidValue * 1));
   }
}
