package com.jsorrell.blockowater.common;

import net.minecraft.item.Item;
import net.minecraft.world.World;

public class ServerProxy {
  public void preInit() { }

  public World getClientWorld() {
    return null;
  }

  public void registerItemRenderer(Item item, int meta, String id) { }
}
