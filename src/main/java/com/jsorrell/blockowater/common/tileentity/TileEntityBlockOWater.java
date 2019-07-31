package com.jsorrell.blockowater.common.tileentity;

import com.jsorrell.blockowater.common.block.BlockOWater;
import com.jsorrell.blockowater.common.util.InfiniteWaterSource;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
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
    if (this.world.isRemote) return;
    for (EnumFacing direction : EnumFacing.VALUES) {
      BlockPos tryPushPos = this.pos.offset(direction);
      TileEntity tryPushTile = this.world.getTileEntity(tryPushPos);
      if (tryPushTile != null) {
        IFluidHandler fluidHandler = tryPushTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, direction.getOpposite());
        if (fluidHandler != null) {
          fluidHandler.fill(new FluidStack(FluidRegistry.WATER, Integer.MAX_VALUE), true);
        }
      } else {
        IBlockState blockState = this.world.getBlockState(tryPushPos);
        // Try to fill cauldron
        if (blockState.getBlock() == Blocks.CAULDRON && blockState.getValue(BlockCauldron.LEVEL) < 3) {
          this.world.setBlockState(tryPushPos, blockState.withProperty(BlockCauldron.LEVEL, 3));
        }
      }
    }
  }
}
