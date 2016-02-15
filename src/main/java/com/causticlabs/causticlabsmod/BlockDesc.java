package com.causticlabs.causticlabsmod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import cofh.lib.util.BlockWrapper;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import tconstruct.world.TinkerWorld;

public enum BlockDesc implements Iterable<BlockWrapper>{
    STONE(
        new BlockWrapper(Blocks.stone, -1),
        new BlockWrapper(Blocks.sandstone, -1),
        new BlockWrapper(Blocks.stonebrick, -1),
        new BlockWrapper(Blocks.cobblestone, -1),
        new BlockWrapper(Blocks.mossy_cobblestone, -1),
        new BlockWrapper(Blocks.sandstone, -1)),
    Aluminum_Ore(new BlockWrapper(TinkerWorld.oreSlag, 5)),
    ARDITE_ORE(new BlockWrapper(TinkerWorld.oreSlag, 2)),
    COBALT_ORE(new BlockWrapper(TinkerWorld.oreSlag, 1)),
    Redstone_Ore(
       new BlockWrapper(Blocks.redstone_ore, -1),
       new BlockWrapper(Blocks.lit_redstone_ore, -1)),
    //Lapis_Ore("minecraft:lapis_ore"),
    //Gold_Ore("minecraft:gold_ore"),
    //Obsidian("minecraft:obsidian"),
    COPPER_ORE("ThermalFoundation:Ore:0"),
    Tin_Ore("ThermalFoundation:Ore:1"),
    Silver_Ore("ThermalFoundation:Ore:2"),
    Lead_Ore("ThermalFoundation:Ore:3"),
    Nickel_Ore("ThermalFoundation:Ore:4"),
    Platinum_Ore("ThermalFoundation:Ore:5"),
    MITHRIL_ORE("ThermalFoundation:Ore:6"),
    ZINC_ORE("Metallurgy:precious.ore:0"),
    MANGANESE_ORE("Metallurgy:base.ore:2"),

    //NETHER_QUARTZ_ORE("minecraft:quartz_ore"),
    //DIAMOND_ORE("minecraft:diamond_ore"),
    //EMERALD_ORE("minecraft:emerald_ore"),

    WOOD(
        new BlockWrapper(Blocks.log, -1),
        new BlockWrapper(Blocks.log2, -1),
        new BlockWrapper(Blocks.planks, -1)),
    DIRT(
       new BlockWrapper(Blocks.dirt, -1),
       new BlockWrapper(Blocks.grass, -1));

   private List<BlockWrapper> _blocks;
   
   @Override
   public Iterator<BlockWrapper> iterator() {
       return _blocks.iterator();
   }

   BlockDesc(Object... objs) {
      _blocks = new ArrayList<BlockWrapper>();
       
      for (Object obj : objs) {
         _blocks.add(Utils.getBlockWrapper(obj));
      }
   }
}
