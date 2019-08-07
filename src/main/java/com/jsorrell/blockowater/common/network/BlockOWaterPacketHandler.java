package com.jsorrell.blockowater.common.network;

import com.jsorrell.blockowater.Values;
import com.jsorrell.blockowater.common.config.PacketConfigSync;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class BlockOWaterPacketHandler {
  public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Values.MOD_ID);
  private static int discriminator = 0;

  public static void registerPacketHandlers() {
    INSTANCE.registerMessage(PacketConfigSync.Handler.class, PacketConfigSync.class, discriminator++, Side.CLIENT);
  }
}
