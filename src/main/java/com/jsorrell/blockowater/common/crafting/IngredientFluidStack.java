package com.jsorrell.blockowater.common.crafting;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class IngredientFluidStack extends Ingredient {
  public final FluidStack fluid;

  protected IngredientFluidStack(FluidStack fluid) {
    super(0);
    this.fluid = fluid;
  }

 protected IngredientFluidStack(Fluid fluid, int amount) {
    this(new FluidStack(fluid, amount));
 }

  @Override
  @Nonnull
  public ItemStack[] getMatchingStacks() {
    return new ItemStack[] { FluidUtil.getFilledBucket(this.fluid) };
  }

  @Override
  public boolean apply(@Nullable ItemStack stack) {
    if (stack == null) return false;

    FluidStack fluidStack = FluidUtil.getFluidContained(stack);

    if (fluidStack == null) return this.fluid == null;
    return fluidStack.containsFluid(this.fluid);
  }
}
