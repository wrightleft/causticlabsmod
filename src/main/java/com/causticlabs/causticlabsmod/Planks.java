package com.causticlabs.causticlabsmod;

import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import tconstruct.tools.TinkerTools;

public class Planks {
   public static void apply(Logger logger) {

      // Add recipes that use TCon tools in a way that damages them. This makes
      // a lot of sense, but is strangely not straight forward. The idea here is
      // that we can't turn a log into planks with our bare hands, we need a tool.

      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Blocks.planks, 1, 0), 20,
         Material.Flint.level(), new ItemStack(Blocks.log, 1, 0), TinkerTools.hatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Blocks.planks, 1, 1), 20,
         Material.Flint.level(), new ItemStack(Blocks.log, 1, 1), TinkerTools.hatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Blocks.planks, 1, 2), 20,
         Material.Flint.level(), new ItemStack(Blocks.log, 1, 2), TinkerTools.hatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Blocks.planks, 1, 3), 20,
         Material.Flint.level(), new ItemStack(Blocks.log, 1, 3), TinkerTools.hatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Blocks.planks, 1, 4), 20,
         Material.Flint.level(), new ItemStack(Blocks.log2, 1, 0), TinkerTools.hatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(new ItemStack(Blocks.planks, 1, 5), 20,
         Material.Flint.level(), new ItemStack(Blocks.log2, 1, 1), TinkerTools.hatchet));
   }
}
