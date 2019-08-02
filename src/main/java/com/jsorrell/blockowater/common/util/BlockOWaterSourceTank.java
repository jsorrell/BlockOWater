package com.jsorrell.blockowater.common.util;

import com.jsorrell.blockowater.common.config.ConfigSettings;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A user-transparent tank that generates water at a consistent rate.
 * Water is meant to be extracted at this rate.
 * The tank should only provide enough buffer for pipes that extract slowly.
 */
public class BlockOWaterSourceTank implements IFluidTank, IFluidHandler {
  private static final int TANK_BUFFER_TIME = 20;
  private int fillCounter = 0;
  private FluidStack fluid;
  private TileEntity tile;

  public BlockOWaterSourceTank(TileEntity tile) {
    this.tile = tile;
    this.fluid = new FluidStack(FluidRegistry.WATER, 0);
  }

  public void tick() {
    if (ConfigSettings.waterGeneration.generationTimer <= ++this.fillCounter) {
      this.fillCounter = 0;
      this.fillInternal(ConfigSettings.waterGeneration.generationAmount);
    }
  }

  @Nullable
  @Override
  public FluidStack getFluid() {
    return fluid;
  }

  @Override
  public int getFluidAmount() {
    if (this.getCapacity() < this.fluid.amount) this.fluid.amount = this.getCapacity();
    return this.fluid.amount;
  }

  @Override
  public int getCapacity() {
    int size;
    if (TANK_BUFFER_TIME <= ConfigSettings.waterGeneration.generationTimer) {
      size = ConfigSettings.waterGeneration.generationAmount;
    } else {
      // Divide rounding up
      int generationEvents = (TANK_BUFFER_TIME + ConfigSettings.waterGeneration.generationTimer - 1) / ConfigSettings.waterGeneration.generationTimer;

      size = ConfigSettings.waterGeneration.generationAmount * generationEvents;
    }

    return Math.max(size, 1000);
  }

  @Override
  public FluidTankInfo getInfo() {
    return new FluidTankInfo(this.getFluid(), this.getCapacity());
  }

  @Override
  public IFluidTankProperties[] getTankProperties() {
    return new IFluidTankProperties[] {
      new IFluidTankProperties() {

        @Nullable
        @Override
        public FluidStack getContents() {
          return BlockOWaterSourceTank.this.getFluid();
        }

        @Override
        public int getCapacity() {
          return BlockOWaterSourceTank.this.getCapacity();
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
          return fluidStack.getFluid() == BlockOWaterSourceTank.this.fluid.getFluid();
        }
      }
    };
  }

  private void fillInternal(int amount) {
    if (amount <= 0) {
      return;
    }

    int filled = this.getCapacity() - fluid.amount;

    if (amount < filled) {
      fluid.amount += amount;
      filled = amount;
    } else {
      fluid.amount = this.getCapacity();
    }

    if (tile != null) {
      FluidEvent.fireEvent(new FluidEvent.FluidFillingEvent(fluid, tile.getWorld(), tile.getPos(), this, filled));
    }
  }

  @Override
  public int fill(FluidStack resource, boolean doFill) {
    return 0;
  }

  @Nullable
  @Override
  public FluidStack drain(FluidStack resource, boolean doDrain) {
    if (resource.getFluid() != this.fluid.getFluid()) return null;
    return drain(resource.amount, doDrain);
  }

  @Nullable
  @Override
  public FluidStack drain(int maxDrain, boolean doDrain) {
    if (maxDrain <= 0) return null;

    int drained = Math.min(this.getFluidAmount(), maxDrain);

    FluidStack stack = new FluidStack(this.fluid.getFluid(), drained);
    if (doDrain) {
      this.fluid.amount -= drained;

      if (tile != null) {
        FluidEvent.fireEvent(new FluidEvent.FluidDrainingEvent(fluid, tile.getWorld(), tile.getPos(), this, drained));
      }
    }
    return stack;
  }

  public void readFromNBT(@Nonnull NBTTagCompound nbt) {
    this.fluid.amount = nbt.getInteger("Amount");
  }

  public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound nbt) {
    nbt.setInteger("Amount", this.fluid.amount);
    return nbt;
  }
}
