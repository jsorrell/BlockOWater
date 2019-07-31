package com.jsorrell.blockowater.common.item;

import com.jsorrell.blockowater.client.BlockOWaterCreativeTab;
import com.jsorrell.blockowater.common.block.BlockMod;
import net.minecraft.item.ItemBlock;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ItemBlockMod extends ItemBlock {
  public ItemBlockMod(@Nonnull BlockMod block) {
    super (block);
    setRegistryName(Objects.requireNonNull(block.getRegistryName()));
    setCreativeTab(BlockOWaterCreativeTab.TAB);
  }
}
