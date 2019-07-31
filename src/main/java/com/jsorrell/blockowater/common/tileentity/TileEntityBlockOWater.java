package com.jsorrell.blockowater.common.tileentity;

import com.jsorrell.blockowater.common.block.BlockOWater;
import com.jsorrell.blockowater.common.util.InfiniteWaterSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityBlockOWater extends TileEntity implements ITickable, ICapabilityProvider {
  public static final String NAME = BlockOWater.NAME;
  private static final int PUSH_COUNTER = 10; // Set by config
  private int tickCount = 0;

  private static final IFluidHandler tank = new InfiniteWaterSource();

  @Override
  public void update() {
    // If config says so
    if (true) {
      if (PUSH_COUNTER <= ++tickCount) {
        tickCount = 0;
        pushWater();
      }
    }
  }

  @Override
  public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
  }

  @Nullable
  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
    if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
      return (T) tank;
    }
    return super.getCapability(capability, facing);
  }

  private void pushWater() {
    // TODO
  }
}
