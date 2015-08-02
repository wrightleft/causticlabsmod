package com.causticlabs.causticlabsmod;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tconstruct.library.tools.ToolCore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapedUseTConToolRecipe extends UseTConToolRecipe {

    static {
        RecipeSorter.register(
            CausticLabsMod.MODID + ":ShapedUseTConToolRecipe",
            ShapedUseTConToolRecipe.class,
            RecipeSorter.Category.SHAPED, "");
    }

    private ItemStack tool;
    private int width = 0;
    private int height = 0;
    private final List<Object> ingredients = new ArrayList<>();
    
    public ShapedUseTConToolRecipe(ItemStack result, int damage, ItemStack tool, Object[][] recipe) {
    	super(result, damage);
    	
    	this.tool = tool;
    	
    	// The height of the recipe is simply the number of rows.
        height = recipe.length;

        // The rows may be different sizes, as Java doesn't prevent that. So use
        // loop through all of them and find the largest size.
        for (Object[] row : recipe) {
            width = Math.max(width, row.length);
        }

        // Create the list of needed ingredients using the width to help fill in
        // nulls where needed.
        for (Object[] row : recipe) {
            for (int i = 0; i < this.width; ++i) {
                if (i < row.length) {
                    ingredients.add(Utils.translateIngredient(row[i]));
                } else {
                    ingredients.add(null);
                }
            }
        }
    }

    @Override
    public boolean matches(InventoryCrafting inventoryCrafting) {
    	// Use a couple of for loops to see if different parts of the overall crafting grid
    	// match the shaped recipe we're looking for.
        for (int x = 0; x <= 3 - width; x++) {
            for (int y = 0; y <= 3 - height; ++y) {
            	// Try matching normally first, and then mirrored. Either one is good.
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
    	boolean toolProvided = false;
    	
    	for (int x = 0; x < 3; ++x) {
    		for (int y = 0; y < 3; ++y) {
    			// Get the ingredient the recipe says we need. This is complicated by the fact that
    			// the recipe can spacially move throughout the crafting grid.
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
                    	ItemStack neededItemStackIngredient = (ItemStack)neededIngredient;
	                    if (neededItemStackIngredient.getItem() == providedIngredient.getItem()) {
                            // Apply the normal rules of matching, which is pretty much just based on damage. 
	                    	// However, the ore dictionary provides a convenient method to handle it for us.
                        	if (!OreDictionary.itemMatches(neededItemStackIngredient, providedIngredient, false)) {
                                return false;
                            }
	                    } else {
	                        // The needed ingredient didn't match the provided one, at all. Bail out.
	                        return false;
	                    }
                    } else {
                    	// We needed an ingredient, but none was found.
                    	return false;
                    }
                } else if (neededIngredient instanceof List) {
                    if (providedIngredient != null) {
	                	// This needed ingredient is a list of ItemStacks. So we need to loop through
	                	// all of them. If we get through all of them without a match, then we failed
	                	// the entire submatch.
	                    List<ItemStack> neededItemStackIngredients = (List<ItemStack>)neededIngredient;
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
                	if (providedIngredient.getItem() instanceof ToolCore) {
                		// The provided ingredient is a TCon tool.
                		if (toolProvided) {
                			// We already have the tool we need. This is an extra tool, so return failure.
                			return false;
                		} else {
                			// We still need a tool, so we need to make sure this is the tool we want,
                			// and that it isn't broken.
                			if (providedIngredient.getItem() == tool.getItem()) {
                				// This is the right tool.
	                            if (!providedIngredient.getTagCompound().getCompoundTag("InfiTool").getBoolean("Broken")) {
	                            	// We found the tool we were looking for. So prevent any other tools from
	                            	// messing us up.
	                            	toolProvided = true;
	                            } else {
	                            	// The tool was broken.
	                            	return false;
	                            }
                			} else {
                				// Not the tool we're looking for.
                				return false;
                			}
                		}
                	} else {
	                	// The recipe didn't need anything in this slot, but there was something
	                	// provided that wasn't a tool. That's a no-no; bail out.
	                    return false;
                	}
                }
    		}
    	}
    	
    	// The only check left is to make sure we have the tool we want.
    	return toolProvided;
    }

    @Override
    public int getRecipeSize() {
        return ingredients.size();
    }
}
