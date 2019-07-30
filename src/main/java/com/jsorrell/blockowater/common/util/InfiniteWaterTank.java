package com.jsorrell.blockowater.common.util;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class InfiniteWaterTank implements IFluidHandler {
  @Override
  public IFluidTankProperties[] getTankProperties() {
    return new IFluidTankProperties[]{ new TankProperties() };
  }

  @Override
  public int fill(FluidStack resource, boolean doFill) {
    return 0;
  }

  @Nullable
  @Override
  public FluidStack drain(FluidStack resource, boolean doDrain) {
    // FIXME Waiting on https://github.com/MinecraftForge/MinecraftForge/issues/5314
    Fluid water = null; // FluidRegistry.WATER;
    if (resource == null) {
      return new FluidStack(water, Integer.MAX_VALUE);
    }
    return null;
  }

  @Nullable
  @Override
  public FluidStack drain(int maxDrain, boolean doDrain) {
    return null;
  }

  private static class TankProperties implements IFluidTankProperties {

    @Nullable
    @Override
    public FluidStack getContents() {
      // FIXME Waiting on https://github.com/MinecraftForge/MinecraftForge/issues/5314
      Fluid water = null; // FluidRegistry.WATER;
      return new FluidStack(water, Integer.MAX_VALUE);
    }

    @Override
    public int getCapacity() {
      return Integer.MAX_VALUE;
    }

    @Override
    public boolean canFill() {
      return false;
    }

    @Override
    public boolean canDrain() {
      return true;
    }

    @Override
    public boolean canFillFluidType(FluidStack fluidStack) {
      return false;
    }

    @Override
    public boolean canDrainFluidType(FluidStack fluidStack) {
      // FIXME Waiting on https://github.com/MinecraftForge/MinecraftForge/issues/5314
      return false;
    }
  }
}
