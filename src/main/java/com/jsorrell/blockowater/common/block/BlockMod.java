package com.jsorrell.blockowater.common.block;

import com.jsorrell.blockowater.BlockOWater;
import com.jsorrell.blockowater.client.BlockOWaterCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public abstract class BlockMod extends Block {
  public BlockMod(Material material, String name) {
    super(material);
    setRegistryName(BlockOWater.MODID, name);
    setUnlocalizedName(name);
    setCreativeTab(BlockOWaterCreativeTab.TAB);
  }

  public void registerItemBlockModel(Item itemBlock) {
    BlockOWater.proxy.registerItemRenderer(itemBlock, 0, this.getRegistryName().getResourcePath());
  }
}
