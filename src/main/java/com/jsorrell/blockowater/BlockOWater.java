package com.jsorrell.blockowater;

import com.jsorrell.blockowater.common.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Values.MOD_ID, name = Values.MOD_NAME, version = Values.MOD_VERSION, useMetadata = true)
public class BlockOWater {
  @Mod.Instance(Values.MOD_ID)
  public static BlockOWater instance;

  @SidedProxy(serverSide = "com.jsorrell.blockowater.common.ServerProxy", clientSide = "com.jsorrell.blockowater.client.ClientProxy")
  public static ServerProxy proxy;

  @Mod.EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    proxy.preInit();
    MinecraftForge.EVENT_BUS.register(this);
  }

  @Mod.EventHandler
  public void init(FMLInitializationEvent event) { }

  @SubscribeEvent
  public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
  {
    if (event.getModID().equals(Values.MOD_ID))
    {
      ConfigManager.sync(Values.MOD_ID, Config.Type.INSTANCE);
    }
  }
}
