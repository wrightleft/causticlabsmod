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
      
      GameRegistry.addRecipe(new ShapedUseTConToolRecipe(
         new ItemStack(TinkerTools.pickaxeHead, 1, HarvestLevel.BRASS.id()),
         Hammer.Brass_Damage * 5, 
         Hammer.Brass_XP * 5,
         TinkerTools.hammer, HarvestLevel.STONE,
         new Object[][] {{"ingotBrass", "ingotBrass", null        },  
                         {null        , "ingotBrass", "ingotBrass"}, 
                         {null        , null        , "ingotBrass"}}));
      
      GameRegistry.addRecipe(new ShapedUseTConToolRecipe(
         new ItemStack(TinkerTools.pickaxeHead, 1, HarvestLevel.BRONZE.id()),
         Hammer.Bronze_Damage * 5, 
         Hammer.Bronze_XP * 5,
         TinkerTools.hammer, HarvestLevel.STONE,
         new Object[][] {{"ingotBronze", "ingotBronze", null         },  
                         {null         , "ingotBronze", "ingotBronze"}, 
                         {null         , null         , "ingotBronze"}}));
      
      GameRegistry.addRecipe(new ShapedUseTConToolRecipe(
         new ItemStack(TinkerTools.pickaxeHead, 1, HarvestLevel.OBSIDIAN.id()), 
         Chisel.Obisdian_Damage * 5, 
         Chisel.Obisdian_XP * 5, 
         TinkerTools.chisel, HarvestLevel.STEEL,
         new Object[][] {{"blockObsidian", "blockObsidian", null           }, 
                         {null           , "blockObsidian", "blockObsidian"}, 
                         {null           , null           , "blockObsidian"}}));

      // Remove all of the casting recipes for the pickaxe head. We'll add our
      // own, with our own values.
      TConstructRegistry.getTableCasting().getCastingRecipes().removeIf(
         recipe -> recipe.output.getItem() == TinkerTools.pickaxeHead);

      // Add the pickaxe head cast with the proper values, and limit the casting of 
      // casts to stone patterns only.
      //
      // The cost of the cast is the inverse of the cost of casting an item from the
      // cast. Go figure. The total cost is a blank cast, which is 8, which is the
      // cost of a large plate.
      // 
      // The magic number 20 is the ticks per second. So 3 * 20 is about 3 seconds.
      // The tick rate can fluctuate, but 20 is the goal.
      
      ItemStack pickaxeHeadCast = new ItemStack(TinkerSmeltery.metalPattern, 1, 2);
      
      TConstructRegistry.getTableCasting().addCastingRecipe(
         pickaxeHeadCast, 
         new FluidStack(TinkerSmeltery.moltenAlubrassFluid, TConstruct.ingotLiquidValue * 3), 
         ItemHelper.stack(TinkerTools.pickaxeHead, 1, HarvestLevel.STONE.id()), 
         true, // Do consume the stone pickaxe head.
         3 * 20);

      // This a list of all of the materials that can be used to cast a pickaxe 
      // head. Certain pickaxe heads can also be created by other means, such as
      // the chisel or hammer, but these are all that use the smeltery.
      //
      // Note that there should be no overlap between chisel, hammer, and 
      // smeltery methods.
      Map<Integer, Fluid> pickaxeCastingMaterials = 
         Stream.of(
            new SimpleEntry<>(HarvestLevel.ALUMITE.id(), TinkerSmeltery.moltenAlumiteFluid),
            new SimpleEntry<>(HarvestLevel.IRON.id(), TinkerSmeltery.moltenIronFluid),
            new SimpleEntry<>(HarvestLevel.INVAR.id(), TinkerSmeltery.moltenInvarFluid),
            new SimpleEntry<>(HarvestLevel.STEEL.id(), TinkerSmeltery.moltenSteelFluid)).collect(
               Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()));
      for (Entry<Integer, Fluid> entry : pickaxeCastingMaterials.entrySet()) {
         TConstructRegistry.getTableCasting().addCastingRecipe(
            ItemHelper.stack(TinkerTools.pickaxeHead, 1, entry.getKey()),
            new FluidStack(entry.getValue(), TConstruct.ingotLiquidValue * 5),
            pickaxeHeadCast,
            false, // Don't consume the cast.
            5 * 20);
      }
   }
}
