package com.causticlabs.causticlabsmod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cofh.lib.util.BlockWrapper;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.oredict.OreDictionary;

public class Utils {

   public static Object translateIngredient(Object object) {
      if (object instanceof ItemStack) {
         return ((ItemStack) object).copy();
      } else if (object instanceof Item) {
         return new ItemStack((Item) object);
      } else if (object instanceof Block) {
         return new ItemStack((Block) object);
      } else if (object instanceof String) {
         return OreDictionary.getOres((String) object);
      } else if (object == null) {
         return null;
      } else {
         throw new RuntimeException("invalid ingredient");
      }
   }

   private static List<BlockWrapper> getBlockWrappersByOreDict(String obj) {
      List<BlockWrapper> blockWrappers = new ArrayList<BlockWrapper>();
      
      List<ItemStack> itemStacks = OreDictionary.getOres(obj);
      for (ItemStack itemStack : itemStacks) {
         if (itemStack.getItem() instanceof ItemBlock) {
            ItemBlock itemBlock = (ItemBlock)itemStack.getItem();
            blockWrappers.add(
               new BlockWrapper(
                  itemBlock.field_150939_a, 
                  itemStack.getItemDamage() == OreDictionary.WILDCARD_VALUE ? 
                     -1 : itemStack.getItemDamage()));
         }
      }
      
      return blockWrappers;
   }

   private static List<BlockWrapper> getBlockWrappersByName(String obj) {
      List<BlockWrapper> blockWrappers = new ArrayList<BlockWrapper>();
      
      // We need to convert the part before the last colon into a Block 
      // by searching block registry. Then we can create a BlockWrapper 
      // by using the meta-data specified after the last colon in the 
      // string.
       
      String[] splitBlockStr = ((String)obj).split(":");
      if (splitBlockStr.length > 1) {
         String blockName = String.format("%s:%s", splitBlockStr[0], splitBlockStr[1]);
         Block block = GameData.getBlockRegistry().getRaw(blockName);
         if (block != null) {
            if (splitBlockStr.length > 2) {
               blockWrappers.add(new BlockWrapper(block, Integer.parseInt(splitBlockStr[2])));
            } else {
               blockWrappers.add(new BlockWrapper(block, -1));
            }
         } else {
            throw new RuntimeException(
               String.format("invalid block name (%s)", blockName));
         }
      } else {
         throw new RuntimeException(
            String.format("invalid block string (%s)", (String)obj));
      }
      
      return blockWrappers;
   }

   public static List<BlockWrapper> getBlockWrappers(Object obj) {
      List<BlockWrapper> blockWrappers = new ArrayList<BlockWrapper>();
      
      if (obj instanceof Block) {
         // To make things easier, just convert a Block to a BlockWrapper.
         blockWrappers.add(new BlockWrapper((Block)obj, -1));
      } else if (obj instanceof BlockWrapper) {
         // Nothing else needs to be done to a BlockWrapper. It's perfect.
         blockWrappers.add((BlockWrapper)obj);
      } else if (obj instanceof String) {
         // If we have a string, it can either be an ore dictionary identifier,
         // or it can be an explicit block name. If it starts with "ore" use
         // the ore dictionary. Lookup the explicit name otherwise.
         String[] splitBlockStr = ((String)obj).split(":");
         if (splitBlockStr.length > 1) {
            if (splitBlockStr[0].equals("ore")) {
               blockWrappers.addAll(getBlockWrappersByOreDict(splitBlockStr[1]));
            } else {
               blockWrappers.addAll(getBlockWrappersByName((String)obj));
            }
         } else {
            throw new RuntimeException(
               String.format("invalid block identifier (%s)", (String)obj));
         }
      } else {
         throw new RuntimeException(
            String.format("invalid block type (%s)", obj.getClass().getName()));
      }
      
      return blockWrappers;
   }
   
   public static <T> T coalesce(T a, T b) {
      return a != null ? a : b;
   }
   public static <T> T coalesce(T a, T b, T c) {
      return a != null ? a : coalesce(b,c);
   }
   public static <T> T coalesce(T a, T b, T c, T d) {
      return a != null ? a : coalesce(b,c,d);
   }
   public static <T> T coalesce(T a, T b, T c, T d, T e) {
      return a != null ? a : coalesce(b,c,d,e);
   }
   
   public static void removeRecipesFor(Item output) {
      List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
      recipes.removeIf(recipe -> 
         (recipe.getRecipeOutput() != null) &&
         (recipe.getRecipeOutput().getItem() == output));
   }

   public static void removeRecipesFor(Block block, int metadata) {
      // There actually isn't any recipes for blocks. Rather we
      // need to find all of the recipes for itemBlocks, which
      // turn into block when placed in the world.
      
      List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
      recipes.removeIf(recipe -> 
         (recipe.getRecipeOutput() != null) &&
         (recipe.getRecipeOutput().getItem() instanceof ItemBlock) &&
         (((ItemBlock)recipe.getRecipeOutput().getItem()).field_150939_a == block) &&
         ((metadata == -1) || (recipe.getRecipeOutput().getItemDamage() == metadata)));
   }
   
   public static void removeRecipesFor(Block block) {
      removeRecipesFor(block, -1);
   }
   
   private static void removeRecipesByOreDict(String name) {
      List<ItemStack> itemStacks = OreDictionary.getOres(name);
      for (ItemStack itemStack : itemStacks) {
         removeRecipesFor(itemStack.getItem());
      }
   }

   private static void removeRecipesByName(String identifier) {
      String[] splitNameStr = identifier.split(":");
      if (splitNameStr.length > 1) {
         // We use the getRaw() function to avoid the weird behavior of 
         // the getObject() function, where it'll return a default object
         // when it can't find a matching one. We'd rather just get null
         // so we can have consistent behavior.
         
         boolean found_it = false;

         String blockName = String.format("%s:%s", splitNameStr[0], splitNameStr[1]);
         Block block = GameData.getBlockRegistry().getRaw(blockName);
         if (block != null) {
            found_it = true;
            
            if (splitNameStr.length > 2) {
               removeRecipesFor(block, Integer.parseInt(splitNameStr[2]));
            } else {
               removeRecipesFor(block);
            }
         } else {
            throw new RuntimeException(
               String.format("invalid name (%s)", blockName));
         }

         GameData.getItemRegistry().getRaw(identifier);
      } else {
         throw new RuntimeException(
            String.format("invalid string (%s)", identifier));
      }
   }
   
   public static void removeRecipesFor(String identifier) {
      String[] splitIdentifierStr = identifier.split(":");
      if (splitIdentifierStr.length > 1) {
         if (splitIdentifierStr[0].equals("ore")) {
            // Ore dictionary entries also don't have meta data, but we
            // won't bother checking for that. We'll just let the next
            // call fail.
            removeRecipesByOreDict(splitIdentifierStr[1]);
         } else {
            // Pass the whole thing on as an identifier. It may have
            // meta data too.
            removeRecipesByName(identifier);
         }
      } else {
         throw new RuntimeException(
            String.format("invalid block identifier (%s)", identifier));
      }
   }
}
