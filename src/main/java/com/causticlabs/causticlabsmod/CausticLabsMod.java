package com.causticlabs.causticlabsmod;

import org.apache.logging.log4j.Logger;

import com.causticlabs.causticlabsmod.tools.Chisel;
import com.causticlabs.causticlabsmod.tools.Hammer;
import com.causticlabs.causticlabsmod.tools.Hatchet;
import com.causticlabs.causticlabsmod.tools.Pickaxe;

import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.TConstructRegistry;
import tconstruct.smeltery.TinkerSmeltery;
import tconstruct.tools.TinkerTools;
import tconstruct.tools.TinkerTools.MaterialID;
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

   public static ItemStack anyHatchetHead;
   public static ItemStack anyShovelHead;
   public static ItemStack anyPickaxeHead;
   public static ItemStack anyKnifeBlade;
   public static ItemStack anyCrossbar;
   public static ItemStack anyBowLimb;
   public static ItemStack anyChiselHead;
   public static ItemStack anyHammerHead;
   public static ItemStack anyToughRod;
   
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
      anyHammerHead = new ItemStack(TinkerTools.hammerHead, 1, OreDictionary.WILDCARD_VALUE);
      anyToughRod = new ItemStack(TinkerTools.toughRod, 1, OreDictionary.WILDCARD_VALUE);
   }

   @Mod.EventHandler
   public void onEvent(FMLInitializationEvent event) {
      MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
      MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
      FMLCommonHandler.instance().bus().register(new ItemCraftedEventHandler());
   }

   @Mod.EventHandler
   public void onEvent(FMLPostInitializationEvent event) {
      // A few vanilla harvest levels get overridden in the static
      // initialization of this ForgeHooks class. Best to get it out of the way 
      // first.
      new ForgeHooks();
      
      // This is where you put things that will interact with other mods.

      // Remove all of the casting recipes for casts.
      TConstructRegistry.getTableCasting().getCastingRecipes().removeIf(
         recipe -> recipe.output.getItem() == TinkerSmeltery.metalPattern);
      
      HarvestLevel.apply(logger);
      Pickaxe.apply(logger);
      Chisel.apply(logger);
      Hammer.apply(logger);
      Hatchet.apply(logger);
      Planks.apply(logger); 
      
      Alumite.apply(logger);
      Steel.apply(logger);

      // Adding this recipe causes any modification normally done in the tool
      // station or forge to be available in normal crafting station or just
      // the inventory crafting grid. Such as repairing a tool, or adding redstone.
      // This will help eliminate the tool forge and tool builder, which are
      // a needless complexity.
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
      
      // TODO - Move these to their own classes.
/*
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
*/
      // Add recipes that use TCon tools in a way that damages them. This makes
      // a lot of sense, but is strangely not straight forward. The idea here is
      // that we can't turn a log into planks with our bare hands, we need a tool.

      GameRegistry.addRecipe(
         new ShapelessUseTConToolRecipe(
            new ItemStack(Items.stick, 2), 
            Hatchet.Wood_Damage * 1, 
            Hatchet.Wood_XP * 1,
            HarvestLevel.FLINT, 
            "plankWood", TinkerTools.hatchet));

      GameRegistry.addRecipe(
         new ShapelessUseTConToolRecipe(
            new ItemStack(TinkerTools.crossbar, 1), 
            Hatchet.Wood_Damage * 1, 
            Hatchet.Wood_XP * 1,
            HarvestLevel.FLINT, 
            "stickWood", TinkerTools.hatchet));
      
      GameRegistry.addRecipe(
         ItemHelper.ShapedRecipe(
            new ItemStack(TinkerTools.toughRod, 1, MaterialID.Wood),
            "  #", 
            " # ",
            "#  ",
            '#', "stickWood"));      
   }
}
