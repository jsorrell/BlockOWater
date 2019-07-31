package com.jsorrell.blockowater.common.block;

import com.jsorrell.blockowater.common.item.ItemBlockMod;
import com.jsorrell.blockowater.common.tileentity.TileEntityBlockOWater;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockOWater extends BlockMod {
  public static final String NAME = "block_o_water";
  public final ItemBlockMod itemBlock;

  public BlockOWater() {
    super(Material.ROCK, BlockOWater.NAME);
    itemBlock = new ItemBlockMod(this);
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
}
