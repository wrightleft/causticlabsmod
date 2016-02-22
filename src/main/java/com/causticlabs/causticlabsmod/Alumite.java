package com.causticlabs.causticlabsmod;

import org.apache.logging.log4j.Logger;

import cofh.lib.util.helpers.FluidHelper;
import net.minecraftforge.fluids.FluidStack;
import scala.actors.threadpool.Arrays;
import tconstruct.TConstruct;
import tconstruct.library.crafting.AlloyMix;
import tconstruct.library.crafting.Smeltery;
import tconstruct.smeltery.TinkerSmeltery;

public class Alumite {
   static void apply(Logger logger) {
      logger.info("applying alumite customizations");
      
      Smeltery.getAlloyList().removeIf(
         alloy -> 
            (alloy.result != null) && 
            (FluidHelper.isFluidEqual(TinkerSmeltery.moltenAlumiteFluid, alloy.result)));
      
      Smeltery.getAlloyList().add(new AlloyMix(
         new FluidStack(TinkerSmeltery.moltenAlumiteFluid, TConstruct.nuggetLiquidValue * 2),
         Arrays.asList(new FluidStack[] {
            new FluidStack(TinkerSmeltery.moltenAluminumFluid, TConstruct.nuggetLiquidValue * 1),
            new FluidStack(TinkerSmeltery.moltenTinFluid, TConstruct.nuggetLiquidValue * 1)
         })));
   }
}
