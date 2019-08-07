package com.jsorrell.blockowater.common;

import net.minecraft.item.Item;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class ServerProxy {
  public World getClientWorld() {
    return null;
  }

  public boolean isClientIntegratedServerRunning() {
    return false;
  }

  public void registerItemRenderer(Item item, int meta, String id) { }

  public void init() { }
}
