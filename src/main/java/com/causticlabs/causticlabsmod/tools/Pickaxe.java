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

public class Pickaxe {

   public static void apply(Logger logger) {

      // Pickaxe Recipes

      ItemStack anyPickaxeHead = CausticLabsMod.anyPickaxeHead;
      
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
         new TConToolPart[][] {{new Head(anyPickaxeHead)        },
                               {new Accessory("materialBinding")},
                               {new Handle("materialRod")       }}));
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
         new TConToolPart[][] {{null                     , null                            , new Head(anyPickaxeHead)   },
                               {null                     , new Accessory("materialBinding"), null                       },
                               {new Handle("materialRod"), null                            , null                       }}));

      // Pickaxe Head Recipes

      // Making a pickaxe head out of flint doesn't require anything special.
      // It's important to keep it this way, as flint is the beginning harvest
      // level.
      GameRegistry.addRecipe(ItemHelper.ShapedRecipe(
         new ItemStack(TinkerTools.pickaxeHead, 1, HarvestLevel.FLINT.id()),
         "## ", 
         " ##", 
         "  #",
         '#', "itemFlint"));
      
      GameRegistry.addRecipe(new ShapedUseTConToolRecipe(
         new ItemStack(TinkerTools.pickaxeHead, 1, HarvestLevel.STONE.id()),
         Chisel.Stone_Damage * 5, 
         Chisel.Stone_XP * 5,
         TinkerTools.chisel, HarvestLevel.FLINT,
         new Object[][] {{"stone", "stone", null   },  
                         {null   , "stone", "stone"}, 
                         {null   , null   , "stone"}}));
      
      GameRegistry.addRecipe(new ShapedUseTConToolRecipe(
         new ItemStack(TinkerTools.pickaxeHead, 1, HarvestLevel.COPPER.id()),
         Hammer.Copper_Damage * 5, 
         Hammer.Copper_XP * 5,
         TinkerTools.hammer, HarvestLevel.STONE,
         new Object[][] {{"ingotCopper", "ingotCopper", null         },  
                         {null         , "ingotCopper", "ingotCopper"}, 
                         {null         , null         , "ingotCopper"}}));
/*
      GameRegistry.addRecipe(new ShapedUseTConToolRecipe(
         PatternBuilder.instance.getToolPart(
            ItemHelper.stack(Blocks.obsidian),
            TConstructRegistry.getItemStack("pickaxeHeadPattern"), 
            null)[0], 
         100 * 5, 
         TinkerTools.chisel,
         HarvestLevel.STEEL,
         new Object[][] {{"blockObsidian", "blockObsidian", null           }, 
                         {null           , "blockObsidian", "blockObsidian"}, 
                         {null           , null           , "blockObsidian"}}));
*/
      // Remove all of the casting recipes for the pickaxe head. We'll add our
      // own, with our own values.
      TConstructRegistry.getTableCasting().getCastingRecipes().removeIf(
         recipe -> recipe.output.getItem() == TinkerTools.pickaxeHead);

      // Add the pickaxe head cast with the proper values, and limit the casting of 
      // casts to stone patterns only.
      //
      // The cost of the cast is the inverse of the cost of casting an item from the
      // cast. Go figure. The total cost is a blank cast, which is 9.
      // 
      // The magic number 20 is the ticks per second. So 4 * 20 is 4 seconds.
      
      ItemStack pickaxeHeadCast = new ItemStack(TinkerSmeltery.metalPattern, 1, 2);
      
      TConstructRegistry.getTableCasting().addCastingRecipe(
         pickaxeHeadCast, 
         new FluidStack(TinkerSmeltery.moltenAlubrassFluid, TConstruct.ingotLiquidValue * 4), 
         ItemHelper.stack(TinkerTools.pickaxeHead, 1, 1), 
         true, // Do consume the stone pickaxe head.
         4 * 20);

      /*
      Map<Integer, Fluid> pickaxeCastingMaterials = 
         Stream.of(
            // Stone must be chiseled, not cast
            new SimpleEntry<>(MaterialID.Bronze, TinkerSmeltery.moltenBronzeFluid),
            new SimpleEntry<>(MaterialID.Iron, TinkerSmeltery.moltenIronFluid),
            new SimpleEntry<>(Utils.getMaterialID("Invar"), TinkerSmeltery.moltenInvarFluid),
            new SimpleEntry<>(MaterialID.Steel, TinkerSmeltery.moltenSteelFluid),
            // Obsidian must be chiseled, not cast
            // Dark Steel (Ender IO)
            new SimpleEntry<>(MaterialID.Alumite, TinkerSmeltery.moltenAlumiteFluid),
            new SimpleEntry<>(MaterialID.Ardite, TinkerSmeltery.moltenArditeFluid),
            new SimpleEntry<>(MaterialID.Cobalt, TinkerSmeltery.moltenCobaltFluid),
            new SimpleEntry<>(MaterialID.Manyullyn, TinkerSmeltery.moltenManyullynFluid)).collect(
               Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()));
      for (Entry<Integer, Fluid> entry : pickaxeCastingMaterials.entrySet()) {
         TConstructRegistry.getTableCasting().addCastingRecipe(
            ItemHelper.stack(TinkerTools.pickaxeHead, 1, entry.getKey()),
            new FluidStack(entry.getValue(), TConstruct.ingotLiquidValue * 5),
            pickaxeHeadCast,
            false, // Don't consume the cast.
            5 * 20);
      }
      */
   }
}
