package com.jsorrell.blockowater.common.block;

import com.jsorrell.blockowater.common.item.ItemBlockMod;
import com.jsorrell.blockowater.common.tileentity.TileEntityBlockOWater;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

public class BlockOWater extends BlockMod {
  public static final String NAME = "block_o_water";
  public final ItemBlockMod itemBlock;

  public BlockOWater() {
    super(Material.ROCK, BlockOWater.NAME);
    itemBlock = new ItemBlockMod(this);
    setHardness(1.5f);
    setHarvestLevel("pickaxe", 1);
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
    return new TileEntityBlockOWater();
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    boolean result = false;
    if (player != null && hand == EnumHand.MAIN_HAND) {
      IFluidHandlerItem fluidHandler = FluidUtil.getFluidHandler(player.getHeldItem(hand));
      if (fluidHandler != null) {
        FluidUtil.interactWithFluidHandler(player, hand, world, pos, facing);
        return true;
      }
    }
    return super.onBlockActivated(world, pos, state, player, hand, facing, hitX, hitY, hitZ);
  }

  @Override
  public int quantityDropped(Random random) {
    return super.quantityDropped(random);
  }
}
