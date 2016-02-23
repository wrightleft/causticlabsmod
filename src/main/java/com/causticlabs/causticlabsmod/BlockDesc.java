package com.causticlabs.causticlabsmod;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cofh.lib.util.BlockWrapper;
import net.minecraft.init.Blocks;
import tconstruct.world.TinkerWorld;

public enum BlockDesc implements Iterable<BlockWrapper>{
    STONE(
        Blocks.stone,
        Blocks.sandstone,
        Blocks.stonebrick,
        Blocks.cobblestone,
        Blocks.mossy_cobblestone,
        Blocks.sandstone),
    ALUMINUM_ORE(new BlockWrapper(TinkerWorld.oreSlag, 5)),
    ARDITE_ORE(new BlockWrapper(TinkerWorld.oreSlag, 2)),
    COBALT_ORE(new BlockWrapper(TinkerWorld.oreSlag, 1)),
    Redstone_Ore(
       Blocks.redstone_ore,
       Blocks.lit_redstone_ore),
    //Lapis_Ore("minecraft:lapis_ore"),
    OBSIDIAN(Blocks.obsidian),
    COPPER_ORE("ThermalFoundation:Ore:0"),
    TIN_ORE("ThermalFoundation:Ore:1"),
    NICKEL_ORE("ThermalFoundation:Ore:4"),
    MITHRIL_ORE("ThermalFoundation:Ore:6"),
    ZINC_ORE("Metallurgy:precious.ore:0"),
    MANGANESE_ORE("Metallurgy:base.ore:2"),
    IRON_ORE(Blocks.iron_ore),
    SHADOW_IRON("Metallurgy:nether.ore:1"),

    Lead_Ore("ThermalFoundation:Ore:3"),
    
    Silver_Ore("ThermalFoundation:Ore:2"),
    //Gold_Ore("minecraft:gold_ore"),
    Platinum_Ore("ThermalFoundation:Ore:5"),
    
    //NETHER_QUARTZ_ORE("minecraft:quartz_ore"),
    //DIAMOND_ORE("minecraft:diamond_ore"),
    //EMERALD_ORE("minecraft:emerald_ore"),

    WOOD(
        Blocks.log,
        Blocks.log2,
        Blocks.planks),
    DIRT(
       Blocks.dirt,
       Blocks.grass),
    
    BRASS_BLOCK("Metallurgy:precious.block:3");

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
         _blocks.add(Utils.getBlockWrapper(obj));
      }
   }
}
