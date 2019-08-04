package com.jsorrell.blockowater.common.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class ShapedRecipe extends ShapedOreRecipe {
  protected ShapedRecipe(ResourceLocation group, @Nonnull ItemStack result, CraftingHelper.ShapedPrimer primer) {
    super(group, result, primer);
  }

  @Override
  @Nonnull
  public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
    NonNullList<ItemStack> remainingItems = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
    for (int i = 0; i < inv.getSizeInventory(); ++i) {
      Ingredient ingredient = this.input.get(i);
      if (ingredient instanceof IngredientFluidStack) {
        ItemStack stack = inv.getStackInSlot(i);
        IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
        if (handler != null) {
          FluidStack requiredFluid = ((IngredientFluidStack) ingredient).fluid;
          handler.drain(requiredFluid, true);
          remainingItems.set(i, handler.getContainer().copy());
        } else {
          // If not IFluidHandlerItem, return the empty container
          ForgeHooks.getContainerItem(stack);
        }
      }
    }
    return remainingItems;
  }
}
