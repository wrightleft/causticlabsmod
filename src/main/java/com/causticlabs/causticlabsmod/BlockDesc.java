package com.causticlabs.causticlabsmod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.Logger;

import cofh.lib.util.BlockWrapper;
import net.minecraft.init.Blocks;
import tconstruct.world.TinkerWorld;

public class BlockDesc implements Iterable<BlockWrapper> {
   public static BlockDesc DIRT;
   public static BlockDesc WOOD;
   public static BlockDesc STONE;
   public static BlockDesc COBBLESTONE;
   public static BlockDesc OBSIDIAN;
   public static BlockDesc IRON_ORE;

   public static BlockDesc LEAD_ORE;
   public static BlockDesc COPPER_ORE;
   public static BlockDesc TIN_ORE;
   public static BlockDesc NICKEL_ORE;
   public static BlockDesc MITHRIL_ORE;

   public static BlockDesc ARDITE_ORE;
   public static BlockDesc COBALT_ORE;
   public static BlockDesc ALUMINUM_ORE;

   public static BlockDesc REDSTONE_ORE;

   // public static BlockDesc SLIVER_ORE = new
   // BlockDesc("ThermalFoundation:Ore:2");
   // public static BlockDesc GOLD_ORE = new BlockDesc("minecraft:gold_ore");
   // public static BlockDesc PLATINUM_ORE = new
   // BlockDesc("ThermalFoundation:Ore:5");

   // NETHER_QUARTZ_ORE("minecraft:quartz_ore"),
   // DIAMOND_ORE("minecraft:diamond_ore"),
   // EMERALD_ORE("minecraft:emerald_ore"),

   public static void postInit(Logger logger) {
      WOOD = new BlockDesc(Blocks.planks, "ore:logWood");
      DIRT = new BlockDesc(Blocks.dirt, Blocks.grass);
      
      STONE = new BlockDesc(Blocks.stone, Blocks.sandstone, Blocks.stonebrick, "HarderUnderground:unstable_stone:0",
         "HarderUnderground:unstable_stone:1");

      COBBLESTONE = new BlockDesc(Blocks.cobblestone, Blocks.mossy_cobblestone, "HarderUnderground:unstable_stone:2",
         "HarderUnderground:unstable_stone:3");

      ALUMINUM_ORE = new BlockDesc(new BlockWrapper(TinkerWorld.oreSlag, 5));

      REDSTONE_ORE = new BlockDesc(Blocks.redstone_ore, Blocks.lit_redstone_ore);

      // Lapis_Ore("minecraft:lapis_ore"),
      OBSIDIAN = new BlockDesc(Blocks.obsidian);

      COPPER_ORE = new BlockDesc("ThermalFoundation:Ore:0");
      TIN_ORE = new BlockDesc("ThermalFoundation:Ore:1");
      NICKEL_ORE = new BlockDesc("ThermalFoundation:Ore:4");
      MITHRIL_ORE = new BlockDesc("ThermalFoundation:Ore:6");

      IRON_ORE = new BlockDesc(Blocks.iron_ore);
      ARDITE_ORE = new BlockDesc(new BlockWrapper(TinkerWorld.oreSlag, 2));
      COBALT_ORE = new BlockDesc(new BlockWrapper(TinkerWorld.oreSlag, 1));

      LEAD_ORE = new BlockDesc("ThermalFoundation:Ore:3");
   }

   private List<BlockWrapper> _blocks;

   @Override
   public Iterator<BlockWrapper> iterator() {
      return _blocks.iterator();
   }

   public BlockWrapper first() {
      return _blocks.get(0);
   }

   BlockDesc(Object... objs) {
      _blocks = new ArrayList<BlockWrapper>();

      // There are several different ways to specify a block. You can
      // pass in an actual Block reference, if you don't care about
      // meta-data. This will have the effect of applying the harvest
      // level to all of the meta-data for that block. You can also pass
      // in a BlockWrapper, which just associates a Block with some
      // meta-data.
      //
      // In addition, to support other mods, where we can't get access to
      // the blocks directly, you can specify a string which will be used
      // to search the block registry. For example,
      // "ThermalFoundation:Ore:1", which will translate to the copper ore
      // block.
      for (Object obj : objs) {
         try {
            _blocks.addAll(Utils.getBlockWrappers(obj));
         } catch (Exception ex) {
            // Ignore any errors. It's possible that a mod wasn't loaded,
            // and so a block wrapper couldn't be made.
         }
      }
   }
}
