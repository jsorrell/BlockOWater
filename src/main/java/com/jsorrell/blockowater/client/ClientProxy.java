package com.jsorrell.blockowater.client;

import com.jsorrell.blockowater.Values;
import com.jsorrell.blockowater.common.ServerProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;

@SuppressWarnings("unused")
public class ClientProxy extends ServerProxy {

  @Override
  public World getClientWorld() {
    return Minecraft.getMinecraft().world;
  }

  @Override
  public boolean isClientIntegratedServerRunning() {
    return Minecraft.getMinecraft().isIntegratedServerRunning();
  }

  @Override
  public void registerItemRenderer(Item item, int meta, String id) {
    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Values.MOD_ID + ":" + id, "inventory"));
  }

  @Override
  public void init() {
    System.out.println("ClientInit");
    MinecraftForge.EVENT_BUS.register(ItemTooltipHandler.INSTANCE);
  }
}
