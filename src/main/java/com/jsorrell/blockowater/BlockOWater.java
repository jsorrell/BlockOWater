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

@Mod(
  modid = BlockOWater.MODID,
  name = BlockOWater.NAME,
  version = BlockOWater.VERSION,
  useMetadata = true,
  acceptedMinecraftVersions = BlockOWater.MC_VERSION)
public class BlockOWater {
  // Mod info
  public static final String MODID = "blockowater";
  public static final String NAME = "Block O' Water";
  public static final String VERSION = "@VERSION@";
  public static final String BUILD_NUMBER = "@BUILD_NUMBER@";
  public static final String MC_VERSION = "@MC_VERSION@";
  public static final String FORGE_VERSION = "@FORGE_VERSION@";

  public static final Logger logger = getLogger();

  @Mod.Instance(BlockOWater.MODID)
  public static BlockOWater instance;

  @SidedProxy(serverSide = "com.jsorrell.blockowater.common.ServerProxy", clientSide = "com.jsorrell.blockowater.client.ClientProxy")
  public static ServerProxy proxy;

  @Nonnull
  private static Logger getLogger() {
    Logger logger = LogManager.getLogger(BlockOWater.NAME);
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
