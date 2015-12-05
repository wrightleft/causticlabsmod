package com.causticlabs.causticlabsmod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tconstruct.library.tools.ToolCore;
import tconstruct.tools.TinkerTools;

// This is a base class for other recipes that want to use a TCon tool. The
// main point is that there is logic to damage the tools and prevent them from
// being consumed.
public abstract class UseTConToolRecipe implements IUseTConToolRecipe {

   private final ItemStack _result;
   private final int _damage;
   private final int _neededHarvestLevel;

   public UseTConToolRecipe(ItemStack result, int damage, int neededHarvestLevel) {
      _result = result;
      _damage = damage;
      _neededHarvestLevel = neededHarvestLevel;
   }

   @Override
   public ItemStack getCraftingResult(InventoryCrafting inventoryCrafting) {
      return _result.copy();
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
               tconstruct.library.tools.AbilityHelper.damageTool(providedIngredient, _damage, player, false);

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
         _neededHarvestLevel;
   }
}
