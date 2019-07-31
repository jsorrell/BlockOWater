package com.jsorrell.blockowater.client;

import com.jsorrell.blockowater.Values;
import com.jsorrell.blockowater.common.ServerProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ClientProxy extends ServerProxy {

  @Override
  public void preInit() {
    super.preInit();
  }

  @Override
  public World getClientWorld() {
    return Minecraft.getMinecraft().world;
  }

  @Override
  public void registerItemRenderer(Item item, int meta, String id) {
    ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(Values.MOD_ID + ":" + id, "inventory"));
  }
}
