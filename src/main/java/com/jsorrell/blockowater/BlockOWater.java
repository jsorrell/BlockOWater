package com.jsorrell.blockowater;

import com.jsorrell.blockowater.common.ServerProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Values.MOD_ID, name = Values.MOD_NAME, version = Values.MOD_VERSION, useMetadata = true)
public class BlockOWater {
  private static final Logger LOGGER = LogManager.getLogger();

  @Mod.Instance(Values.MOD_ID)
  public static BlockOWater instance;

  @SidedProxy(serverSide = "com.jsorrell.blockowater.common.ServerProxy", clientSide = "com.jsorrell.blockowater.client.ClientProxy")
  public static ServerProxy proxy;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    proxy.preInit();
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) { }
}
