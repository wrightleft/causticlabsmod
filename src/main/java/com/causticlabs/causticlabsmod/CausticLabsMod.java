package com.causticlabs.causticlabsmod;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;

import tconstruct.library.TConstructRegistry;
import tconstruct.library.crafting.PatternBuilder;
import tconstruct.tools.TinkerTools;
import tconstruct.weaponry.TinkerWeaponry;

// There are a few more dependencies, but they have wildcard dependencies of their own which preclude us depending
// on them.
//
// denseores
// Iguana's Tinkers Tweaks
@Mod(modid = CausticLabsMod.MODID, name = CausticLabsMod.NAME, version = CausticLabsMod.VERSION, dependencies = "required-after:TConstruct;required-after:ThermalFoundation")
public class CausticLabsMod {
   public static final String MODID = "causticlabsmod";
   public static final String NAME = "Caustic Labs Mod";
   public static final String VERSION = "1.7.10-1.0";

   private Logger logger;

   @Mod.EventHandler
   public void onEvent(FMLPreInitializationEvent event) {

      logger = event.getModLog();

      // Adding this recipe causes any modification normally done in the tool
      // station or forge to be available in normal crafting station or just
      // the inventory crafting grid.
      GameRegistry.addRecipe(new ShapelessTConModRecipe());

      // Fix the ore dictionary for TCon in a couple of places to make the
      // recipes easier.

      OreDictionary.registerOre("materialRod", new ItemStack(TinkerTools.toolRod, 1, OreDictionary.WILDCARD_VALUE));
      for (ItemStack itemStack : OreDictionary.getOres("stickWood")) {
         OreDictionary.registerOre("materialRod", itemStack);
      }

      OreDictionary.registerOre("materialBinding", new ItemStack(TinkerTools.binding, 1, OreDictionary.WILDCARD_VALUE));
      OreDictionary.registerOre("materialBinding", new ItemStack(Items.string, 1, OreDictionary.WILDCARD_VALUE));

      // Add recipes for tools. This bypasses the stupid pattern thing and
      // the part builder nonsense.

      ItemStack anyHatchetHead = new ItemStack(TinkerTools.hatchetHead, 1, OreDictionary.WILDCARD_VALUE);
      ItemStack anyShovelHead = new ItemStack(TinkerTools.shovelHead, 1, OreDictionary.WILDCARD_VALUE);
      ItemStack anyPickaxeHead = new ItemStack(TinkerTools.pickaxeHead, 1, OreDictionary.WILDCARD_VALUE);
      ItemStack anyKnifeBlade = new ItemStack(TinkerTools.knifeBlade, 1, OreDictionary.WILDCARD_VALUE);
      ItemStack anyCrossbar = new ItemStack(TinkerTools.crossbar, 1, OreDictionary.WILDCARD_VALUE);
      ItemStack anyBowLimb = new ItemStack(TinkerWeaponry.partBowLimb, 1, OreDictionary.WILDCARD_VALUE);
      ItemStack anyChiselHead = new ItemStack(TinkerTools.chiselHead, 1, OreDictionary.WILDCARD_VALUE);

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
      
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyHatchetHead,
            "materialRod", 
            anyShovelHead, 
            new Object[][] {{anyHatchetHead, null         , anyShovelHead},
                            {null          , "materialRod", null          }}));

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
      
      ItemStack bowstring = new ItemStack(TinkerWeaponry.bowstring);
      GameRegistry.addRecipe(new ShapedTConToolRecipe(
            anyBowLimb,
            bowstring, 
            anyBowLimb, 
            new Object[][] {{null      , anyBowLimb},
                            {anyBowLimb, bowstring }}));

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

      // Add Recipes with Tools

      ItemStack anyHatchet = new ItemStack(TinkerTools.hatchet, 1, OreDictionary.WILDCARD_VALUE);

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

      GameRegistry
            .addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Items.stick, 2), 10, "plankWood", anyHatchet));

      GameRegistry.addRecipe(
            new ShapelessUseTConToolRecipe(new ItemStack(TinkerTools.crossbar, 1), 10, "stickWood", anyHatchet));
   }

   @Mod.EventHandler
   public void onEvent(FMLInitializationEvent event) {
      MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
      MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
      FMLCommonHandler.instance().bus().register(new ItemCraftedEventHandler());
   }

   @Mod.EventHandler
   public void onEvent(FMLPostInitializationEvent event) {
      HarvestLevel.apply(logger);

      ItemStack anyChisel = new ItemStack(TinkerTools.chisel, 1, OreDictionary.WILDCARD_VALUE);

      GameRegistry.addRecipe(new ShapedUseTConToolRecipe(
         PatternBuilder.instance.getToolPart(
            new ItemStack(Blocks.stone),
            new ItemStack(TinkerTools.woodPattern, 1, 2), 
            null)[0], 
         20, 
         anyChisel, 
         new Object[][] {{"stone", "stone", null   }, 
                         {null   , "stone", "stone"}, 
                         {null   , null   , "stone"}}));
   }

}
