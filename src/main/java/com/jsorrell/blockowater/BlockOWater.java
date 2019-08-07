package com.jsorrell.blockowater;

import com.jsorrell.blockowater.common.ServerProxy;
import com.jsorrell.blockowater.common.config.ConfigProcessor;
import com.jsorrell.blockowater.common.network.BlockOWaterPacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(modid = Values.MOD_ID, name = Values.MOD_NAME, version = Values.MOD_VERSION, useMetadata = true)
public class BlockOWater {
  public static final Logger logger = getLogger();

  @Mod.Instance(Values.MOD_ID)
  public static BlockOWater instance;

  @SidedProxy(serverSide = "com.jsorrell.blockowater.common.ServerProxy", clientSide = "com.jsorrell.blockowater.client.ClientProxy")
  public static ServerProxy proxy;

  @Nonnull
  private static Logger getLogger() {
    Logger logger = LogManager.getLogger(Values.MOD_NAME);
    if (logger == null) {
      throw new NullPointerException("failed to acquire logger");
    }
    return logger;
  }

  @Mod.EventHandler
  @SuppressWarnings("unused")
  public void init(FMLInitializationEvent event) {
    BlockOWaterPacketHandler.registerPacketHandlers();
    MinecraftForge.EVENT_BUS.register(ConfigProcessor.INSTANCE);
    proxy.init();
  }
}
