package com.jsorrell.blockowater.common.tileentity;

import com.jsorrell.blockowater.common.block.BlockOWater;
import com.jsorrell.blockowater.common.config.ConfigSettings;
import com.jsorrell.blockowater.common.util.BlockOWaterSourceTank;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
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
  private int tickCount = 0;

  private BlockPos previousPos = this.pos;
  private Boolean biomeValid;

  private final BlockOWaterSourceTank tank = new BlockOWaterSourceTank(this);

  @Override
  public void update() {
    if (world.isRemote) return;

    // Updated when moved with something like Mekanism Cardboard Box
    if (this.pos != this.previousPos) {
      this.previousPos = this.pos;
      biomeValid = checkBiomeValid();
    }

    if (!biomeValid) return;

    this.tank.tick();

    if (ConfigSettings.pushConfig.canPush) {
      if (ConfigSettings.pushConfig.pushTimer <= ++tickCount) {
        tickCount = 0;
        pushWater();
      }
    }
  }

  private boolean checkBiomeValid() {
    if (!ConfigSettings.workingConditions.requiresWaterBiome && ConfigSettings.workingConditions.worksInNether) {
      return true;
    }

    Biome biome = this.world.getBiome(this.pos);

    if (ConfigSettings.workingConditions.requiresWaterBiome) {
      return BiomeDictionary.hasType(biome, BiomeDictionary.Type.OCEAN) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.RIVER);
    }

    if (!ConfigSettings.workingConditions.worksInNether) {
      return !BiomeDictionary.hasType(biome, BiomeDictionary.Type.NETHER);
    }

    throw new RuntimeException("Should never get here.");
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
          int pushAmount = fluidHandler.fill(new FluidStack(FluidRegistry.WATER, ConfigSettings.pushConfig.pushAmount), false);
          FluidStack drain = tank.drain(pushAmount, true);
          pushAmount = drain == null ? 0 : drain.amount;
          fluidHandler.fill(new FluidStack(FluidRegistry.WATER, pushAmount), true);
        }
      } else {
        IBlockState blockState = this.world.getBlockState(tryPushPos);
        // Try to fill cauldron
        if (blockState.getBlock() == Blocks.CAULDRON) {
          int cauldronLevel = blockState.getValue(BlockCauldron.LEVEL);

          /* Push Rate Fill */
          // TODO maybe have a config option for cauldron push rate
          int pushAmount = ConfigSettings.pushConfig.pushAmount;
          // Ensure even with a slow push rate the cauldron fills eventually
          if (pushAmount < 334)
             pushAmount = Math.random() * 1000./3. < (double)pushAmount ? 334 : 0;
          int cauldronNewLevelPush = getMaxCauldronLevel(cauldronLevel, pushAmount);

          /* Water Available Fill */
          int cauldronNewLevel = Math.min(cauldronNewLevelPush, getMaxCauldronLevel(cauldronLevel, this.tank.getFluidAmount()));

          /* Fill Cauldron */
          this.tank.drain(getCauldronFluidIncrease(cauldronLevel, cauldronNewLevel), true);
          if (cauldronNewLevel != cauldronLevel)
            this.world.setBlockState(tryPushPos, blockState.withProperty(BlockCauldron.LEVEL, cauldronNewLevel));
        }
      }
    }
  }

  private int getCauldronFluidAmount(int level) {
    return (int)((double)level * 1000./3.);
  }

  private int getCauldronFluidIncrease(int currentLevel, int newLevel) {
    return getCauldronFluidAmount(newLevel) - getCauldronFluidAmount(currentLevel);
  }

  private int getMaxCauldronLevel(int currentLevel, int fluidAmount) {
    int finalLevel = currentLevel;
    for (; finalLevel < 3; ++finalLevel) {
      if (fluidAmount < getCauldronFluidIncrease(currentLevel, finalLevel + 1)) break;
    }
    return finalLevel;
  }

  @Override
  public void readFromNBT(@Nonnull NBTTagCompound compound) {
    NBTTagCompound tankNBT = compound.getCompoundTag("Tank");
    this.tank.readFromNBT(tankNBT);
    super.readFromNBT(compound);
  }

  @Nonnull
  @Override
  public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
    NBTTagCompound tankNBT = new NBTTagCompound();
    this.tank.writeToNBT(tankNBT);
    compound.setTag("Tank", tankNBT);
    return super.writeToNBT(compound);
  }
}
