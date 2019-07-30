package com.jsorrell.blockowater.common.tileentity;

import com.jsorrell.blockowater.common.block.BlockOWater;
import com.jsorrell.blockowater.common.core.BlockOWaterTileEntityTypes;
import com.jsorrell.blockowater.common.util.InfiniteWaterTank;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityBlockOWater extends TileEntity implements ITickableTileEntity, ICapabilityProvider {
  public static final String NAME = BlockOWater.NAME;
  private static final int PUSH_COUNTER = 10; // Set by config
  private int tickCount = 0;

  protected InfiniteWaterTank tank = new InfiniteWaterTank();
  private final LazyOptional<IFluidHandler> holder = LazyOptional.of(() -> tank);

  public TileEntityBlockOWater() {
    super(BlockOWaterTileEntityTypes.blockOWater);
  }

  @Override
  public void tick() {
    // If config says so
    if (true) {
      if (PUSH_COUNTER <= ++tickCount) {
        tickCount = 0;
        pushWater();
      }
    }
  }

  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
    if (!this.removed && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
      return holder.cast();
    }
    return super.getCapability(cap, side);
  }

  private void pushWater() {
    // TODO
  }

}
