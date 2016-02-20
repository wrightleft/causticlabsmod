package com.causticlabs.causticlabsmod;

import iguanaman.iguanatweakstconstruct.leveling.LevelingLogic;
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
   private final int _xp;
   private final HarvestLevel _neededHarvestLevel;

   public UseTConToolRecipe(ItemStack result, int damage, int xp, HarvestLevel neededHarvestLevel) {
      _result = result;
      _damage = damage;
      _xp = xp;
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
               // The chisel is sort of already made for use during crafting, 
               // but none of the other tools are, so there are some special
               // cases here.
               
               // The chisel damages itself by 1, so we can just account for that and
               // take one off our damage.
               
               // The chisel gives itself 1 experience already, so we also need to 
               // account for that.
               int realDamage = _damage;
               int realXP = _xp;
               if (providedIngredient.getItem() == TinkerTools.chisel) {
                  --realDamage;
                  --realXP;
               }
               
               tconstruct.library.tools.AbilityHelper.damageTool(providedIngredient, realDamage, player, false);
               LevelingLogic.addXP(providedIngredient, player, realXP);
               
               // The chisel already handles not consuming itself.
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
         _neededHarvestLevel.level();
   }
}
