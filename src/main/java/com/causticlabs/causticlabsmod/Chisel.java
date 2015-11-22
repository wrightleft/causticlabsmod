package com.causticlabs.causticlabsmod;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.Logger;

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
   public static void apply(Logger logger) {

      // Chisel Recipes
      
      ItemStack anyChiselHead = CausticLabsMod.anyChiselHead;
      
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyChiselHead,
            "materialRod",
            new Object[][] {{anyChiselHead},
                            {"materialRod"}}));
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyChiselHead,
            "materialRod",
            new Object[][] {{null         , anyChiselHead},
                            {"materialRod", null         }}));
      
      // Chisel Head Recipes

      GameRegistry.addRecipe(ItemHelper.ShapedRecipe(
         PatternBuilder.instance.getToolPart(
            new ItemStack(Items.flint),
            TConstructRegistry.getItemStack("chiselHeadPattern"), 
            null)[0],
         "###", " # ", " # ",
         '#', "itemFlint"));
      
      GameRegistry.addRecipe(new ShapedUseTConToolRecipe(
         PatternBuilder.instance.getToolPart(
            new ItemStack(Blocks.stone),
            TConstructRegistry.getItemStack("chiselHeadPattern"), 
            null)[0], 
         10 * 5, 
         TinkerTools.chisel,
         HarvestLevel.FLINT,
         new Object[][] {{"stone", "stone", "stone" }, 
                         {null   , "stone", null    }, 
                         {null   , "stone", null    }}));
      
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
      
      // Remove all of the casting recipes for the chisel head. We'll add our
      // own, with our own values.
      TConstructRegistry.getTableCasting().getCastingRecipes().removeIf(
         recipe -> recipe.output.getItem() == TinkerTools.chiselHead);

      // Add the chisel head cast with the proper values, and limit the casting of 
      // casts to stone patterns only.
      //
      // The cost of the cast is the inverse of the cost of casting an item from the
      // cast. Go figure. The total cost is a blank cast, which is 9.
      // 
      // The magic number 20 is the ticks per second. So 4 * 20 is 4 seconds.
      
      ItemStack chiselHeadCast = ItemHelper.stack(TinkerSmeltery.metalPattern, 1, 13);
      
      TConstructRegistry.getTableCasting().addCastingRecipe(
         chiselHeadCast, 
         new FluidStack(TinkerSmeltery.moltenAlubrassFluid, TConstruct.ingotLiquidValue * 4), 
         ItemHelper.stack(TinkerTools.chiselHead, 1, 1), 
         true, // Do consume the stone chisel head.
         4 * 20);

      Map<Integer, Fluid> chiselCastingMaterials = 
         Stream.of(
            // Stone must be chiseled, not cast.
            new SimpleEntry<>(MaterialID.Bronze, TinkerSmeltery.moltenBronzeFluid),
            new SimpleEntry<>(MaterialID.Iron, TinkerSmeltery.moltenIronFluid),
            new SimpleEntry<>(Utils.getMaterialID("Invar"), TinkerSmeltery.moltenInvarFluid),
            new SimpleEntry<>(MaterialID.Steel, TinkerSmeltery.moltenSteelFluid),
            // TODO - Obsidian must be chiseled, not cast.
            // TODO - Dark Steel (Ender IO)
            new SimpleEntry<>(MaterialID.Alumite, TinkerSmeltery.moltenAlumiteFluid),
            new SimpleEntry<>(MaterialID.Ardite, TinkerSmeltery.moltenArditeFluid),
            new SimpleEntry<>(MaterialID.Cobalt, TinkerSmeltery.moltenCobaltFluid),
            new SimpleEntry<>(MaterialID.Manyullyn, TinkerSmeltery.moltenManyullynFluid)).collect(
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
