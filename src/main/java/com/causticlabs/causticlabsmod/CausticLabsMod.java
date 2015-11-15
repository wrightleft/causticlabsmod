package com.causticlabs.causticlabsmod;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.AbstractMap.SimpleEntry;

import org.apache.logging.log4j.Logger;

import cofh.api.modhelpers.ThermalExpansionHelper;
import cofh.lib.util.helpers.ItemHelper;
import tconstruct.TConstruct;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.library.tools.ToolMaterial;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tconstruct.tools.TinkerTools.MaterialID;
import tconstruct.tools.items.ToolPart;
import tconstruct.weaponry.TinkerWeaponry;

// There are a few more dependencies, but they have wildcard dependencies of their own which preclude us depending
// on them.
//
// denseores
// Iguana's Tinkers Tweaks
@Mod(modid = CausticLabsMod.MODID, name = CausticLabsMod.NAME, version = CausticLabsMod.VERSION, dependencies = "required-after:TConstruct;required-after:ThermalFoundation;required-after:ThermalExpansion")
public class CausticLabsMod {
   public static final String MODID = "causticlabsmod";
   public static final String NAME = "Caustic Labs Mod";
   public static final String VERSION = "1.7.10-1.0";

   private static Logger logger;

   private static ItemStack anyHatchetHead;
   private static ItemStack anyShovelHead;
   private static ItemStack anyPickaxeHead;
   private static ItemStack anyKnifeBlade;
   private static ItemStack anyCrossbar;
   private static ItemStack anyBowLimb;
   private static ItemStack anyChiselHead;
   
   private static ItemStack anyHatchet;
   private static ItemStack anyChisel;
   
   @Mod.EventHandler
   public void onEvent(FMLPreInitializationEvent event) { 
      logger = event.getModLog();

      anyHatchetHead = new ItemStack(TinkerTools.hatchetHead, 1, OreDictionary.WILDCARD_VALUE);
      anyShovelHead = new ItemStack(TinkerTools.shovelHead, 1, OreDictionary.WILDCARD_VALUE);
      anyPickaxeHead = new ItemStack(TinkerTools.pickaxeHead, 1, OreDictionary.WILDCARD_VALUE);
      anyKnifeBlade = new ItemStack(TinkerTools.knifeBlade, 1, OreDictionary.WILDCARD_VALUE);
      anyCrossbar = new ItemStack(TinkerTools.crossbar, 1, OreDictionary.WILDCARD_VALUE);
      anyBowLimb = new ItemStack(TinkerWeaponry.partBowLimb, 1, OreDictionary.WILDCARD_VALUE);
      anyChiselHead = new ItemStack(TinkerTools.chiselHead, 1, OreDictionary.WILDCARD_VALUE);
      
      anyHatchet = new ItemStack(TinkerTools.hatchet, 1, OreDictionary.WILDCARD_VALUE);
      anyChisel = new ItemStack(TinkerTools.chisel, 1, OreDictionary.WILDCARD_VALUE);
   }

   @Mod.EventHandler
   public void onEvent(FMLInitializationEvent event) {
      MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
      MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
      FMLCommonHandler.instance().bus().register(new ItemCraftedEventHandler());
   }

   @Mod.EventHandler
   public void onEvent(FMLPostInitializationEvent event) {
      // This is where you put things that will interact with other mods.
      
      HarvestLevel.apply(logger);
      Steel.apply(logger);

      // Adding this recipe causes any modification normally done in the tool
      // station or forge to be available in normal crafting station or just
      // the inventory crafting grid. Such as repairing a tool, or adding redstone.
      // This will help eliminate the tool forge and tool builder.
      GameRegistry.addRecipe(new ShapelessTConModRecipe());

      
      
      // Fix the ore dictionary for TCon in a couple of places to make the
      // recipes easier.
      
      // Make any kind of tool rod available as a generic rod.
      OreDictionary.registerOre("materialRod", new ItemStack(TinkerTools.toolRod, 1, OreDictionary.WILDCARD_VALUE));
      
      // Make every stick available as a handle.
      for (ItemStack itemStack : OreDictionary.getOres("stickWood")) {
         OreDictionary.registerOre("materialRod", itemStack);
      }

      // Make every binding available as a generic binding.
      OreDictionary.registerOre("materialBinding", new ItemStack(TinkerTools.binding, 1, OreDictionary.WILDCARD_VALUE));
      
      // Make every kind of string available as a binding.
      OreDictionary.registerOre("materialBinding", new ItemStack(Items.string, 1, OreDictionary.WILDCARD_VALUE));

      
      
      // Add recipes for tool parts and tools. This helps us bypasses the stupid pattern
      // system and the part builder nonsense and make tools right in the crafting
      // table.
      //
      // Most of these tools have multiple recipes to make things easier. Such as a slanted
      // one and a up and down one. Nearly all of the Shaped recipes are also mirrored for
      // convenience. 

      // Hatchet
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
         anyHatchetHead, 
         "materialRod", 
         new Object[][] {{anyHatchetHead},
                         {"materialRod" }}));
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
         anyHatchetHead, 
         "materialRod", 
         new Object[][] {{null         , anyHatchetHead},
                         {"materialRod", null          }}));

      // Shovel
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyShovelHead, 
            "materialRod", 
            new Object[][] {{anyShovelHead},
                            {"materialRod"}}));
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyShovelHead, 
            "materialRod", 
            new Object[][] {{null         , anyShovelHead},
                            {"materialRod", null          }}));
      
      // Mattock
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyHatchetHead,
            "materialRod", 
            anyShovelHead, 
            new Object[][] {{anyHatchetHead, null         , anyShovelHead},
                            {null          , "materialRod", null          }}));

      // Pickaxe
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyPickaxeHead,
            "materialRod", 
            "materialBinding", 
            new Object[][] {{anyPickaxeHead   },
                            {"materialBinding"},
                            {"materialRod"    }}));
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyPickaxeHead,
            "materialRod", 
            "materialBinding", 
            new Object[][] {{null         , null             , anyPickaxeHead   },
                            {null         , "materialBinding", null             },
                            {"materialRod", null             , null             }}));

      // Dagger
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyKnifeBlade,
            "materialRod", 
            anyCrossbar, 
            new Object[][] {{anyKnifeBlade},
                            {anyCrossbar  },
                            {"materialRod"}}));
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyKnifeBlade,
            "materialRod", 
            anyCrossbar, 
            new Object[][] {{null         , null       , anyKnifeBlade},
                            {null         , anyCrossbar, null         },
                            {"materialRod", null       , null         }}));
      
      // Short Bow
      ItemStack bowstring = new ItemStack(TinkerWeaponry.bowstring);
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyBowLimb,
            bowstring, 
            anyBowLimb, 
            new Object[][] {{null      , anyBowLimb},
                            {anyBowLimb, bowstring }}));

      // Throwing Knifes
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyKnifeBlade,
            "materialRod",
            new Object[][] {{anyKnifeBlade},
                            {"materialRod"}}));
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyKnifeBlade,
            "materialRod",
            new Object[][] {{null         , anyKnifeBlade},
                            {"materialRod", null         }}));

      // Chisel
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

      // Add recipes that use TCon tools in a way that damages them. This makes
      // a lot of sense, but is strangely not straight forward. The idea here is
      // that we can't turn a log into planks with our bare hands, we need a tool.

      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Blocks.planks, 1, 0), 20,
         new ItemStack(Blocks.log, 1, 0), anyHatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Blocks.planks, 1, 1), 20,
         new ItemStack(Blocks.log, 1, 1), anyHatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Blocks.planks, 1, 2), 20,
         new ItemStack(Blocks.log, 1, 2), anyHatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Blocks.planks, 1, 3), 20,
         new ItemStack(Blocks.log, 1, 3), anyHatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Blocks.planks, 1, 4), 20,
         new ItemStack(Blocks.log2, 1, 0), anyHatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Blocks.planks, 1, 5), 20,
         new ItemStack(Blocks.log2, 1, 1), anyHatchet));

      GameRegistry.addRecipe(
         new ShapelessUseTConToolRecipe(new ItemStack(Items.stick, 2), 10, "plankWood", anyHatchet));

      GameRegistry.addRecipe(
         new ShapelessUseTConToolRecipe(new ItemStack(TinkerTools.crossbar, 1), 10, "stickWood", anyHatchet));
      
      
      

      
      
      // Pickaxe Head
      GameRegistry.addRecipe(new ShapedUseTConToolRecipe(
         PatternBuilder.instance.getToolPart(
            new ItemStack(Blocks.stone),
            TConstructRegistry.getItemStack("pickaxeHeadPattern"), 
            null)[0], 
         20, 
         anyChisel, 
         new Object[][] {{"stone", "stone", null   }, 
                         {null   , "stone", "stone"}, 
                         {null   , null   , "stone"}}));
      
      // Remove all of the casting recipes for casts, and other ones that we want to change.
      TConstructRegistry.getTableCasting().getCastingRecipes().removeIf(
         recipe -> 
            (recipe.output.getItem() == TinkerSmeltery.metalPattern) ||
            (recipe.output.getItem() == TinkerTools.pickaxeHead));
      
      // Add back the pickaxe head cast with the proper values, and limit the casting of 
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
         new ItemStack(TinkerTools.pickaxeHead, 1, 1), 
         true, 
         4 * 20);

      Map<Integer, Fluid> pickaxeCastingMaterials = 
         Stream.of(
            new SimpleEntry<>(MaterialID.Bronze, TinkerSmeltery.moltenBronzeFluid),
            new SimpleEntry<>(MaterialID.Iron, TinkerSmeltery.moltenIronFluid),
            new SimpleEntry<>(Utils.getMaterialID("Invar"), TinkerSmeltery.moltenInvarFluid),
            new SimpleEntry<>(MaterialID.Steel, TinkerSmeltery.moltenSteelFluid),
            new SimpleEntry<>(MaterialID.Obsidian, TinkerSmeltery.moltenObsidianFluid),
            // Dark Steel (Ender IO)
            new SimpleEntry<>(MaterialID.Alumite, TinkerSmeltery.moltenAlumiteFluid),
            new SimpleEntry<>(MaterialID.Ardite, TinkerSmeltery.moltenArditeFluid),
            new SimpleEntry<>(MaterialID.Cobalt, TinkerSmeltery.moltenCobaltFluid),
            new SimpleEntry<>(MaterialID.Manyullyn, TinkerSmeltery.moltenManyullynFluid)).collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue()));
      for (Entry<Integer, Fluid> entry : pickaxeCastingMaterials.entrySet()) {
         TConstructRegistry.getTableCasting().addCastingRecipe(
            new ItemStack(TinkerTools.pickaxeHead, 1, entry.getKey()),
            new FluidStack(entry.getValue(), TConstruct.ingotLiquidValue * 5),
            pickaxeHeadCast,
            false,
            5 * 20);
      }
   }

}
