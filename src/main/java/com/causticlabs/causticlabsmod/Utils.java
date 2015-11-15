package com.causticlabs.causticlabsmod;

import java.util.Map.Entry;

import cpw.mods.fml.common.event.FMLInterModComms;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
   
   public static int getMaterialID(String name) {
      for (Entry<Integer, ToolMaterial> entry : TConstructRegistry.toolMaterials.entrySet()) {
         if (entry.getValue().name() == name) {
            return entry.getKey();
         }
      }
      
      throw new RuntimeException("invalid name");
   }
}
