package com.causticlabs.causticlabsmod;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import com.causticlabs.causticlabsmod.tools.Hatchet;

import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tconstruct.tools.TinkerTools;

public class Planks {
   public static void postInit(Logger logger) {

      // Add recipes that use TCon tools in a way that damages them. This makes
      // a lot of sense, but is strangely not straight forward. The idea here is
      // that we can't turn a log into planks with our bare hands, we need a tool.

      // TODO - Do we want to add another tool, like the battleaxe?

      // We want to replace the vanilla recipes for planks, since they don't
      // make and sense. No one can convert logs into plants with their bare
      // hands.
      //
      // Too accomplish this, we'll look for all the recipes that produce planks
      // and replace them with ones that have the same input and output, but which
      // use a tool.
      //
      // This lets us be a bit more dynamic when new mods add logs.
      
      List<IRecipe> previousRecipes = new ArrayList<IRecipe>();
      for (IRecipe recipe : (List<IRecipe>)CraftingManager.getInstance().getRecipeList()) {
         if ((recipe.getRecipeOutput() != null) && ItemHelper.isOreNameEqual(recipe.getRecipeOutput(), "plankWood")) {
            previousRecipes.add(recipe);
         }
      }
      
      Utils.removeRecipesFor("ore:plankWood");

      // Since we're trying to be all fancy with this, and merely
      // enumerating all of the recipes explicitly, we need to make
      // sure all of our assumptions are true. Such as the recipe
      // size. We only plan on replacing recipes and take one log
      // and magically turn it into 4 planks.
      for (IRecipe recipe : previousRecipes) {
         if (recipe instanceof ShapedRecipes) {
            ShapedRecipes shapedRecipe = (ShapedRecipes)recipe;
                        
            if (shapedRecipe.recipeItems.length == 1) {
               GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(
                  ItemHelper.cloneStack(shapedRecipe.getRecipeOutput(), 2), 
                  Hatchet.Wood_Damage * 1,
                  Hatchet.Wood_XP * 1,
                  HarvestLevel.FLINT, 
                  shapedRecipe.recipeItems[0], TinkerTools.hatchet));
            }
         } else if (recipe instanceof ShapelessRecipes) {
            ShapelessRecipes shapelessRecipe = (ShapelessRecipes)recipe;
            
            if (shapelessRecipe.recipeItems.size() == 1) {
               GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(
                  ItemHelper.cloneStack(shapelessRecipe.getRecipeOutput(), 2), 
                  Hatchet.Wood_Damage * 1,
                  Hatchet.Wood_XP * 1,
                  HarvestLevel.FLINT, 
                  shapelessRecipe.recipeItems.get(0), TinkerTools.hatchet));
            }
         } else if (recipe instanceof ShapelessOreRecipe) {
            ShapelessOreRecipe shapelessOreRecipe = (ShapelessOreRecipe)recipe;
            
            if (shapelessOreRecipe.getInput().size() == 1) {
               GameRegistry.addRecipe(new ShapelessUseTConToolRecipe(
                  ItemHelper.cloneStack(shapelessOreRecipe.getRecipeOutput(), 2), 
                  Hatchet.Wood_Damage * 1,
                  Hatchet.Wood_XP * 1,
                  HarvestLevel.FLINT, 
                  shapelessOreRecipe.getInput().get(0), TinkerTools.hatchet));
            }
         }
      }
   }
}
