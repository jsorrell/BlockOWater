package com.jsorrell.blockowater.client;

import com.jsorrell.blockowater.BlockOWater;
import com.jsorrell.blockowater.common.ServerProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@SuppressWarnings("unused")
public class ClientProxy extends ServerProxy {

  @Override
  public World getClientWorld() {
    return Minecraft.getMinecraft().world;
  }

  @Override
  public boolean isClientSinglePlayer() {
    return Minecraft.getMinecraft().isSingleplayer();
  }

  @Override
  public void registerItemRenderer(Item item, int meta, String id) {
    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(BlockOWater.MODID + ":" + id, "inventory"));
  }

  @Override
  public void init() {
    MinecraftForge.EVENT_BUS.register(ItemTooltipHandler.INSTANCE);
  }
}
