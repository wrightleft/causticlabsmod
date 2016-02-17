package com.causticlabs.causticlabsmod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import tconstruct.library.crafting.ToolBuilder;

/**
 * This class provides a shaped recipe to create TConTools without the goofball 
 * crafting tables provided in Tinkers' Construct.
 * 
 * The recipe must include the same parts that TCon uses. Use the nested classes
 * to decorate the recipe items.
 */
public class ShapedTConToolRecipe implements IRecipe {

   static {
      RecipeSorter.register(CausticLabsMod.MODID + ":ShapedTConToolRecipe", ShapedTConToolRecipe.class,
            RecipeSorter.Category.SHAPED, "");
   }
   
   public static class TConToolPart {
      public Object obj;
      public TConToolPart(Object obj) {
         this.obj = obj;
      }
   }
   
   public static class Head extends TConToolPart { public Head(Object obj) { super(obj); } }
   public static class Handle extends TConToolPart { public Handle(Object obj) { super(obj); } }
   public static class Accessory extends TConToolPart { public Accessory(Object obj) { super(obj); } }
   public static class Extra extends TConToolPart { public Extra(Object obj) { super(obj); } }

   private Object head = null;
   private Object handle = null;
   private Object accessory = null;
   private Object extra = null;

   private int width = 0;
   private int height = 0;
   private final List<Object> ingredients = new ArrayList<>();
   
   public ShapedTConToolRecipe(TConToolPart[][] recipe) {
      // The height of the recipe is simply the number of rows.
      height = recipe.length;

      // The rows may be different sizes, as Java doesn't prevent that. So
      // loop through all of them and find the largest size.
      for (Object[] row : recipe) {
         width = Math.max(width, row.length);
      }

      // Now we can iterate over the recipe and make sure it makes sense. A 
      // TConTool recipe needs at least a head and a handle.
      //
      // Optionally, an accessory and an extra part can be provided, in that
      // order. You can't have an extra part without an accessory. Any other
      // items in the recipe don't make sense. Duplicates also don't make sense.
      //
      // As we find the items we're looking for, we'll save them off so we can
      // later use them to identify the provided ingredients when creating the
      // result.
      //
      // We also create the list of needed ingredients using the width to help 
      // fill in nulls where needed. This list will be used for matching the
      // shape of the provided ingredients.
      for (TConToolPart[] row : recipe) {
         for (int i = 0; i < this.width; ++i) {
            if (i < row.length) {
               if (row[i] instanceof Head) {
                  if (head == null) {
                     head = Utils.translateIngredient(row[i].obj);
                     ingredients.add(head);
                  } else {
                     throw new RuntimeException("multiple heads in TConTool recipe");
                  }
               } else if (row[i] instanceof Handle) {
                  if (handle == null) {
                     handle = Utils.translateIngredient(row[i].obj);
                     ingredients.add(handle);
                  } else {
                     throw new RuntimeException("multiple handles in TConTool recipe");
                  }
               } else if (row[i] instanceof Accessory) {
                  if (accessory == null) {
                     accessory = Utils.translateIngredient(row[i].obj);
                     ingredients.add(accessory);
                  } else {
                     throw new RuntimeException("multiple accessories in TConTool recipe");
                  }
               } else if (row[i] instanceof Extra) {
                  if (extra == null) {
                     extra = Utils.translateIngredient(row[i].obj);
                     ingredients.add(extra);
                  } else {
                     throw new RuntimeException("multiple extras in TConTool recipe");
                  }
               } else if (row[i] == null) {
                  ingredients.add(null);
               } else {
                  throw new RuntimeException("invalid part in TConTool recipe");
               }
            } else {
               ingredients.add(null);
            }
         }
      }
      
      if (head == null) {
         throw new RuntimeException("missing head in TConTool recipe");
      }
      
      if (handle == null) {
         throw new RuntimeException("missing handle in TConTool recipe");
      }
      
      if ((extra != null) && (accessory == null)) {
         throw new RuntimeException("missing accessory when extra provided in TConTool recipe");
      }
   }

   @Override
   public boolean matches(InventoryCrafting inventoryCrafting, World world) {
      // Use a couple of for loops to see if different parts of the overall
      // crafting grid match the shaped recipe we're looking for.
      for (int x = 0; x <= 3 - width; x++) {
         for (int y = 0; y <= 3 - height; ++y) {
            // Try matching normally first, and then mirrored. Either one is
            // good.
            if (submatches(inventoryCrafting, x, y, false)) {
               return true;
            } else if (submatches(inventoryCrafting, x, y, true)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean submatches(InventoryCrafting inventory, int startX, int startY, boolean mirror) {
      for (int x = 0; x < 3; ++x) {
         for (int y = 0; y < 3; ++y) {
            // Get the ingredient the recipe says we need. This is complicated
            // by the fact that
            // the recipe can spatially move throughout the crafting grid.
            int subX = x - startX;
            int subY = y - startY;
            Object neededIngredient = null;

            if (subX >= 0 && subY >= 0 && subX < width && subY < height) {
               if (mirror) {
                  neededIngredient = ingredients.get((width - subX - 1) + (subY * width));
               } else {
                  neededIngredient = ingredients.get(subX + (subY * width));
               }
            }

            ItemStack providedIngredient = inventory.getStackInRowAndColumn(x, y);

            if (neededIngredient instanceof ItemStack) {
               if (providedIngredient != null) {
                  ItemStack neededItemStackIngredient = (ItemStack) neededIngredient;
                  if (neededItemStackIngredient.getItem() == providedIngredient.getItem()) {
                     // Apply the normal rules of matching, which is pretty much
                     // just based on damage. However, the ore dictionary
                     // provides a convenient method to handle it for us.
                     if (!OreDictionary.itemMatches(neededItemStackIngredient, providedIngredient, false)) {
                        return false;
                     }
                  } else {
                     // The needed ingredient didn't match the provided one, at
                     // all. Bail out.
                     return false;
                  }
               } else {
                  // We needed an ingredient, but none was found.
                  return false;
               }
            } else if (neededIngredient instanceof List) {
               if (providedIngredient != null) {
                  // This needed ingredient is a list of ItemStacks. So we need
                  // to loop through all of them. If we get through all of them
                  // without a match, then we failed the entire submatch.
                  List<ItemStack> neededItemStackIngredients = (List<ItemStack>) neededIngredient;
                  boolean matched = false;
                  for (ItemStack neededItemStackIngredient : neededItemStackIngredients) {
                     if (OreDictionary.itemMatches(neededItemStackIngredient, providedIngredient, false)) {
                        matched = true;
                        break;
                     }
                  }

                  if (!matched) {
                     return false;
                  }
               } else {
                  // We needed an ingredient, but none was found.
                  return false;
               }
            } else if (providedIngredient != null) {
               // The recipe didn't need anything in this slot, but there was
               // something provided. That's a no-no; bail out.
               return false;
            }
         }
      }

      return true;
   }
   
   private boolean matches(ItemStack providedIngredient, Object object) {
      if (object instanceof ItemStack) {
         ItemStack ingredient = (ItemStack) object;
         return ingredient.getItem() == providedIngredient.getItem();
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
   }

   @Override
   public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
      ItemStack providedHead = null;
      ItemStack providedHandle = null;
      ItemStack providedAccessory = null;
      ItemStack providedExtra = null;

      for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
         ItemStack providedIngredient = inventoryCrafting.getStackInSlot(i);
         if (providedIngredient != null) {
            if ((providedHead == null) && matches(providedIngredient, head)) {
               providedHead = providedIngredient;
            } else if ((providedHandle == null) && matches(providedIngredient, handle)) {
               providedHandle = providedIngredient;
            } else if ((accessory != null) && (providedAccessory == null) && matches(providedIngredient, accessory)) {
               providedAccessory = providedIngredient;
            } else if ((extra != null) && (providedExtra == null) && matches(providedExtra, extra)) {
               providedExtra = providedIngredient;
            }
         }
      }

      if (providedExtra != null) {
         return ToolBuilder.instance.buildTool(providedHead, providedHandle, providedAccessory, providedExtra, null);
      } else if (providedAccessory != null) {
         return ToolBuilder.instance.buildTool(providedHead, providedHandle, providedAccessory, null, null);
      } else {
         return ToolBuilder.instance.buildTool(providedHead, providedHandle, null, null, null);
      }
   }

   @Override
   public ItemStack getRecipeOutput() {
      return null;
   }

   @Override
   public int getRecipeSize() {
      return ingredients.size();
   }
}
