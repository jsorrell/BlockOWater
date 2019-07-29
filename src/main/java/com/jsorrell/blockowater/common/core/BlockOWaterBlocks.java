package com.jsorrell.blockowater.common.core;

import com.jsorrell.blockowater.Values;
import com.jsorrell.blockowater.common.block.BlockOWater;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Values.MOD_ID)
public class BlockOWaterBlocks {
  @ObjectHolder(BlockOWater.NAME)
  public static BlockOWater blockOWater;
  @ObjectHolder(BlockOWater.NAME)
  public static BlockItem blockOWaterItemBlock;

  @Mod.EventBusSubscriber(modid = Values.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class Registration {
    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
      IForgeRegistry<Block> blockRegistry = event.getRegistry();
      blockRegistry.register(new com.jsorrell.blockowater.common.block.BlockOWater());
    }

    @SubscribeEvent
    public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
      IForgeRegistry<Item> itemBlockRegistry = event.getRegistry();
      itemBlockRegistry.register(blockOWater.itemBlock);
    }
  }
}
