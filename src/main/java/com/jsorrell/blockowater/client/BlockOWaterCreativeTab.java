package com.jsorrell.blockowater.client;

import com.jsorrell.blockowater.common.core.BlockOWaterBlocks;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class BlockOWaterCreativeTab extends CreativeTabs {
  public static final BlockOWaterCreativeTab TAB = new BlockOWaterCreativeTab();
  public static final String NAME = "itemGroupBlockOWater";

  private BlockOWaterCreativeTab() {
    super(NAME);
  }

  @Override
  @Nonnull
  public ItemStack getTabIconItem() {
    return new ItemStack(BlockOWaterBlocks.blockOWaterItemBlock);
  }
}
