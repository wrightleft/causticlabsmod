package com.causticlabs.causticlabsmod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import tconstruct.library.tools.ToolCore;

public class ShapelessUseTConToolRecipe extends UseTConToolRecipe {

   private final List<Object> ingredients = new ArrayList<>();

   static {
      RecipeSorter.register(CausticLabsMod.MODID + ":ShapelessUseTConToolRecipe", ShapelessUseTConToolRecipe.class,
            RecipeSorter.Category.SHAPELESS, "");
   }

   public ShapelessUseTConToolRecipe(
         ItemStack result, 
         int damage,  
         int xp,
         HarvestLevel neededHarvestLevel, 
         Object... ingredients) {
      super(result, damage, xp, neededHarvestLevel);

      // Build our list of ingredients by converting whatever object we got
      // into an ItemStack or a list of ItemStacks.
      for (Object ingredient : ingredients) {
         this.ingredients.add(Utils.translateIngredient(ingredient));
      }
   }

   @Override
   public boolean matches(InventoryCrafting inventoryCrafting) {
      ArrayList<Object> unmatchedIngredients = new ArrayList<>(ingredients);
      for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
         ItemStack providedIngredient = inventoryCrafting.getStackInSlot(i);
         if (providedIngredient != null) {
            if (!unmatchedIngredients.removeIf((Object object) -> {
               if (object instanceof ItemStack) {
                  ItemStack ingredient = (ItemStack) object;
                  if (ingredient.getItem() == providedIngredient.getItem()) {
                     if (ingredient.getItem() instanceof ToolCore) {
                        // The needed ingredient is a TCon tool, then
                        // all we need to do is to make sure the provided one
                        // isn't broken.
                        return notBroken(providedIngredient) && canHarvest(providedIngredient);
                     } else {
                        // The item we're trying to match isn't the tool. So we
                        // can apply the normal rules of matching, which is
                        // pretty much just based on damage.
                        //
                        // Either we allow any damage, or we need to match a
                        // specific damage. The item we're trying to match isn't
                        // the tool. So we can apply the normal rules of
                        // matching, which is pretty much just based on damage.
                        // However, the ore dictionary provides a convenient
                        // method to handle it for us.
                        return OreDictionary.itemMatches(ingredient, providedIngredient, false);
                     }
                  } else {
                     // The needed ingredient didn't match the provided one.
                     return false;
                  }
               } else if (object instanceof List) {
                  List<ItemStack> ingredients = (List<ItemStack>) object;
                  for (ItemStack ingredient : ingredients) {
                     if (OreDictionary.itemMatches(ingredient, providedIngredient, false)) {
                        return true;
                     }
                  }

                  return false;
               } else {
                  return false;
               }
            })) {
               // If we couldn't remove anything from the list of needed
               // ingredients, then we must not match.
               return false;
            }
         } else {
            // The spot in the crafting inventory was empty. Move on to the
            // next one.
         }
      }

      return unmatchedIngredients.isEmpty();
   }

   @Override
   public int getRecipeSize() {
      return ingredients.size();
   }
}
