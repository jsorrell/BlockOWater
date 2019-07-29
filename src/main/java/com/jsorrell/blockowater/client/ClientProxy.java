package com.jsorrell.blockowater.client;

import com.jsorrell.blockowater.common.ServerProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class ClientProxy extends ServerProxy {

  @Override
  public void preInit() {
    super.preInit();
  }

  @Override
  public World getClientWorld() {
    return Minecraft.getInstance().world;
  }
}
