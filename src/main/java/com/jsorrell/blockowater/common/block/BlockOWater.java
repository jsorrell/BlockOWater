package com.jsorrell.blockowater.common.block;

import com.jsorrell.blockowater.common.item.ItemBlockMod;
import net.minecraft.block.material.Material;

public class BlockOWater extends BlockMod {
  public static final String NAME = "block_o_water";
  public final ItemBlockMod itemBlock;

  public BlockOWater() {
    super(Material.ROCK, BlockOWater.NAME);
    itemBlock = new ItemBlockMod(this);
  }
}
