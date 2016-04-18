package com.causticlabs.causticlabsmod;

import org.apache.logging.log4j.Logger;

import com.causticlabs.causticlabsmod.tools.Hatchet;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import tconstruct.tools.TinkerTools;

public class Planks {
   public static void postInit(Logger logger) {

      // Add recipes that use TCon tools in a way that damages them. This makes
      // a lot of sense, but is strangely not straight forward. The idea here is
      // that we can't turn a log into planks with our bare hands, we need a tool.

      // TODO - Do we want to add another tool, like the battleaxe?

      Utils.removeRecipesFor(Blocks.planks);
      
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(
         new ItemStack(Blocks.planks, 1, 0), 
         Hatchet.Wood_Damage * 1,
         Hatchet.Wood_XP * 1,
         HarvestLevel.FLINT, 
         new ItemStack(Blocks.log, 1, 0), TinkerTools.hatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(
         new ItemStack(Blocks.planks, 1, 1),  
         Hatchet.Wood_Damage * 1,
         Hatchet.Wood_XP * 1,
         HarvestLevel.FLINT, 
         new ItemStack(Blocks.log, 1, 1), TinkerTools.hatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(
         new ItemStack(Blocks.planks, 1, 2),  
         Hatchet.Wood_Damage * 1,
         Hatchet.Wood_XP * 1,
         HarvestLevel.FLINT, 
         new ItemStack(Blocks.log, 1, 2), TinkerTools.hatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(
         new ItemStack(Blocks.planks, 1, 3),  
         Hatchet.Wood_Damage * 1,
         Hatchet.Wood_XP * 1,
         HarvestLevel.FLINT, 
         new ItemStack(Blocks.log, 1, 3), TinkerTools.hatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(
         new ItemStack(Blocks.planks, 1, 4),  
         Hatchet.Wood_Damage * 1,
         Hatchet.Wood_XP * 1,
         HarvestLevel.FLINT, 
         new ItemStack(Blocks.log2, 1, 0), TinkerTools.hatchet));
      GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(
         new ItemStack(Blocks.planks, 1, 5),  
         Hatchet.Wood_Damage * 1,
         Hatchet.Wood_XP * 1,
         HarvestLevel.FLINT, 
         new ItemStack(Blocks.log2, 1, 1), TinkerTools.hatchet));
   }
}
