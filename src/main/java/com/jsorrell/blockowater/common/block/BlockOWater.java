package com.jsorrell.blockowater.common.block;

import com.jsorrell.blockowater.common.item.BlockItemMod;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class BlockOWater extends BlockMod {
  public static final String NAME = "block_o_water";
  public final BlockItemMod itemBlock;

  public BlockOWater() {
    super(Properties.create(Material.ROCK, MaterialColor.WATER), BlockOWater.NAME);

    itemBlock = new BlockItemMod(this);
  }
}
