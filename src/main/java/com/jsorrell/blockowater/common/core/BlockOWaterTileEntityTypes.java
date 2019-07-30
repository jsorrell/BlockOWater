package com.jsorrell.blockowater.common.core;

import com.jsorrell.blockowater.Values;
import com.jsorrell.blockowater.common.tileentity.TileEntityBlockOWater;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.Supplier;

@ObjectHolder(Values.MOD_ID)
public class BlockOWaterTileEntityTypes {
  @ObjectHolder(TileEntityBlockOWater.NAME)
  public static TileEntityType<?> blockOWater;

  @Mod.EventBusSubscriber(modid = Values.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
  public static class Registration {
    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
      IForgeRegistry<TileEntityType<?>> tileEntityRegistry = event.getRegistry();
      tileEntityRegistry.register(TileEntityType.Builder.create((Supplier<TileEntity>) TileEntityBlockOWater::new, BlockOWaterBlocks.blockOWater).build(null).setRegistryName(Values.MOD_ID, TileEntityBlockOWater.NAME));
    }
  }
}
