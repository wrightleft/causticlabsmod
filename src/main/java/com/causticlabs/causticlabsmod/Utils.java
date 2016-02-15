package com.causticlabs.causticlabsmod;

import java.util.Map.Entry;

import cofh.lib.util.BlockWrapper;
import cpw.mods.fml.common.registry.GameData;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.TConstructRegistry;
import tconstruct.library.tools.ToolMaterial;

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
   
   public static BlockWrapper getBlockWrapper(Object obj) {
      if (obj instanceof Block) {
         // To make things easier, just convert a Block to a BlockWrapper.
         return new BlockWrapper((Block)obj, -1);
      } else if (obj instanceof BlockWrapper) {
         // Nothing else needs to be done to a BlockWrapper. It's perfect.
         return (BlockWrapper)obj;
      } else if (obj instanceof String) {
         // If we have a string, we need to convert the part before the 
         // last colon into a Block by searching block registry. Then we can 
         // create a BlockWrapper by using the meta-data specified after 
         // the last colon in the string.
          
         String[] splitBlockStr = ((String)obj).split(":");
         if (splitBlockStr.length > 1) {
            String blockName = String.format("%s:%s", splitBlockStr[0], splitBlockStr[1]);
            Block block = GameData.getBlockRegistry().getRaw(blockName);
            if (block != null) {
               if (splitBlockStr.length > 2) {
                  return new BlockWrapper(block, Integer.parseInt(splitBlockStr[2]));
               } else {
                  return new BlockWrapper(block, -1);
               }
            } else {
               throw new RuntimeException(
                  String.format("invalid block name (%s)", blockName));
            }
         } else {
            throw new RuntimeException(
               String.format("invalid block string (%s)", (String)obj));
         }
      } else {
         throw new RuntimeException(
            String.format("invalid block type (%s)", obj.getClass().getName()));
      }
   }
}
