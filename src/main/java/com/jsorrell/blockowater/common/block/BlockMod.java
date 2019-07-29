package com.jsorrell.blockowater.common.block;

import com.jsorrell.blockowater.Values;
import net.minecraft.block.Block;

public abstract class BlockMod extends Block {
  public BlockMod(Block.Properties properties, String name) {
    super(properties);
    setRegistryName(Values.MOD_ID, name);
  }
}
