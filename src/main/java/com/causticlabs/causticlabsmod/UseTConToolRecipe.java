package com.causticlabs.causticlabsmod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import tconstruct.library.tools.ToolCore;
import tconstruct.tools.TinkerTools;

// This is a base class for other recipes that want to use a TCon tool. The
// main point is that there is logic to damage the tools and prevent them from
// being consumed.
public abstract class UseTConToolRecipe implements IUseTConToolRecipe {

   private final ItemStack result;
   private final int damage;
   private HarvestLevel neededHarvestLevel;

   public UseTConToolRecipe(ItemStack result, int damage, HarvestLevel neededHarvestLevel) {
      this.result = result;
      this.damage = damage;
      this.neededHarvestLevel = neededHarvestLevel;
   }

   @Override
   public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
      return result.copy();
   }

   @Override
   public ItemStack getRecipeOutput() {
      return null;
   }

   @Override
   public boolean matches(InventoryCrafting inventoryCrafting, World world) {
      return matches(inventoryCrafting);
   }

   @Override
   public void damageTools(InventoryCrafting inventoryCrafting, EntityPlayer player) {
      for (int i = 0; i < inventoryCrafting.getSizeInventory(); i++) {
         ItemStack providedIngredient = inventoryCrafting.getStackInSlot(i);
         if (providedIngredient != null) {
            if (providedIngredient.getItem() instanceof ToolCore) {
               tconstruct.library.tools.AbilityHelper.damageTool(providedIngredient, damage, player, false);

               // The chisel already handles not consuming itself during
               // crafting. None of the other tools do though. The method
               // that the chisel uses doesn't allow for the damage to be
               // part of the recipe, which is a mistake.
               if (providedIngredient.getItem() != TinkerTools.chisel) {
                  ++providedIngredient.stackSize;
               }
            }
         }
      }
   }

   public boolean notBroken(ItemStack providedIngredient) {
      return !providedIngredient.getTagCompound().getCompoundTag("InfiTool").getBoolean("Broken");
   }

   public boolean canHarvest(ItemStack tool) {
      return 
         tool.getTagCompound().getCompoundTag("InfiTool").getInteger("HarvestLevel") >= 
         neededHarvestLevel.level;
   }
}
