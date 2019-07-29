package com.jsorrell.blockowater.common.item;

import com.jsorrell.blockowater.client.BlockOWaterItemGroup;
import com.jsorrell.blockowater.common.block.BlockMod;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.Objects;

public class BlockItemMod extends BlockItem {
  public BlockItemMod(@Nonnull BlockMod block) {
    super (block, new Item.Properties().group(BlockOWaterItemGroup.GROUP));
    setRegistryName(Objects.requireNonNull(block.getRegistryName()));
  }
}
