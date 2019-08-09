package com.jsorrell.blockowater.common.core;

import com.jsorrell.blockowater.BlockOWater;
import com.jsorrell.blockowater.common.tileentity.TileEntityBlockOWater;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(com.jsorrell.blockowater.BlockOWater.MODID)
public class BlockOWaterBlocks {
  @ObjectHolder(com.jsorrell.blockowater.common.block.BlockOWater.NAME)
  public static com.jsorrell.blockowater.common.block.BlockOWater blockOWater;
  @ObjectHolder(com.jsorrell.blockowater.common.block.BlockOWater.NAME)
  public static ItemBlock blockOWaterItemBlock;

  @Mod.EventBusSubscriber(modid = BlockOWater.MODID)
  public static class Registration {
    @SubscribeEvent
    public static void registerBlocks(final RegistryEvent.Register<Block> event) {
      IForgeRegistry<Block> blockRegistry = event.getRegistry();
      blockRegistry.register(new com.jsorrell.blockowater.common.block.BlockOWater());
      // Register tile entities - In later Forge these will have their own RegistryEvent
      GameRegistry.registerTileEntity(TileEntityBlockOWater.class, new ResourceLocation(BlockOWater.MODID, TileEntityBlockOWater.NAME));
    }

    @SubscribeEvent
    public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
      IForgeRegistry<Item> itemBlockRegistry = event.getRegistry();
      itemBlockRegistry.register(blockOWater.itemBlock);
    }

    @SubscribeEvent
    public static void registerItemBlockModels(ModelRegistryEvent event) {
      blockOWater.registerItemBlockModel(Item.getItemFromBlock(blockOWater));
    }
  }
}
