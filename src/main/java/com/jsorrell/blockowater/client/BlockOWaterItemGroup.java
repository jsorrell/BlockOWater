package com.jsorrell.blockowater.client;

import com.jsorrell.blockowater.common.core.BlockOWaterBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class BlockOWaterItemGroup extends ItemGroup {
  public static BlockOWaterItemGroup GROUP = new BlockOWaterItemGroup();

  private BlockOWaterItemGroup() {
    super("itemGroupBlockOWater");
  }

  @Override
  @Nonnull
  public ItemStack createIcon() {
    return new ItemStack(BlockOWaterBlocks.blockOWaterItemBlock);
  }
}
