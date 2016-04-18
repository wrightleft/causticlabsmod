package com.causticlabs.causticlabsmod.tools;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Logger;

import com.causticlabs.causticlabsmod.CausticLabsMod;
import com.causticlabs.causticlabsmod.HarvestLevel;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe;
import com.causticlabs.causticlabsmod.ShapedTConToolRecipe.*;
import com.causticlabs.causticlabsmod.ShapedUseTConToolRecipe;

import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tconstruct.tools.TinkerTools.MaterialID;

public class Chisel {   
   public static final int StoneDamage = 10;
   public static final int StoneXP = 5;
   public static final int ObisdianDamage = 100;
   public static final int ObisdianXP = 50;
   
   public static void postInit(Logger logger) {
      logger.info("Chisel - Post Initialization");

      // Chisel Recipes
      
      ItemStack anyChiselHead = CausticLabsMod.anyChiselHead;
      
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            new TConToolPart[][] {{new Head(anyChiselHead)  },
                                  {new Handle("materialRod")}}));
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            new TConToolPart[][] {{null                     , new Head(anyChiselHead)},
                                  {new Handle("materialRod"), null                   }}));
      
      // Chisel Head Recipes

      GameRegistry.addRecipe(ItemHelper.ShapedRecipe(
         new ItemStack(TinkerTools.chiselHead, 1, HarvestLevel.FLINT.id()),
         "###", 
         " # ", 
         " # ",
         '#', "itemFlint"));
      
      GameRegistry.addRecipe(new ShapedUseTConToolRecipe(
         new ItemStack(TinkerTools.chiselHead, 1, HarvestLevel.STONE.id()),
         Chisel.StoneDamage * 5, 
         Chisel.StoneXP * 5,
         TinkerTools.chisel, HarvestLevel.FLINT,
         new Object[][] {{"stone", "stone", "stone" }, 
                         {null   , "stone", null    }, 
                         {null   , "stone", null    }}));
/*      
      GameRegistry.addRecipe(new ShapedUseTConToolRecipe(
         PatternBuilder.instance.getToolPart(
            new ItemStack(Blocks.obsidian),
            TConstructRegistry.getItemStack("chiselHeadPattern"), 
            null)[0], 
         100 * 5, 
         TinkerTools.chisel,
         HarvestLevel.FLINT,
         new Object[][] {{"blockObsidian", "blockObsidian", "blockObsidian" }, 
                         {null           , "blockObsidian", null            }, 
                         {null           , "blockObsidian", null            }}));
*/
      // Remove all of the casting recipes for the chisel head. We'll add our
      // own, with our own values.
      TConstructRegistry.getTableCasting().getCastingRecipes().removeIf(
         recipe -> recipe.output.getItem() == TinkerTools.chiselHead);

      // Add the chisel head cast with the proper values, and limit the casting of 
      // casts to stone patterns only.
      //
      // The cost of the cast is the inverse of the cost of casting an item from the
      // cast. Go figure. The total cost is a blank cast, which is 8.
      // 
      // The magic number 20 is the ticks per second. So 4 * 20 is 4 seconds.
      
      ItemStack chiselHeadCast = ItemHelper.stack(TinkerSmeltery.metalPattern, 1, 13);
      
      TConstructRegistry.getTableCasting().addCastingRecipe(
         chiselHeadCast, 
         new FluidStack(TinkerSmeltery.moltenAlubrassFluid, TConstruct.ingotLiquidValue * 3), 
         ItemHelper.stack(TinkerTools.chiselHead, 1, 1), 
         true, // Do consume the stone chisel head.
         3 * 20);

      Map<Integer, Fluid> chiselCastingMaterials = 
         Stream.of(
            new SimpleEntry<>(HarvestLevel.ALUMITE.id(), TinkerSmeltery.moltenAlumiteFluid),
            new SimpleEntry<>(HarvestLevel.IRON.id(), TinkerSmeltery.moltenIronFluid),
            new SimpleEntry<>(HarvestLevel.INVAR.id(), TinkerSmeltery.moltenInvarFluid),
            new SimpleEntry<>(HarvestLevel.STEEL.id(), TinkerSmeltery.moltenSteelFluid)).collect(
               Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()));
      for (Entry<Integer, Fluid> entry : chiselCastingMaterials.entrySet()) {
         TConstructRegistry.getTableCasting().addCastingRecipe(
            ItemHelper.stack(TinkerTools.chiselHead, 1, entry.getKey()),
            new FluidStack(entry.getValue(), TConstruct.ingotLiquidValue * 5),
            chiselHeadCast,
            false, // Don't consume the cast.
            5 * 20);
      }
   }
}
