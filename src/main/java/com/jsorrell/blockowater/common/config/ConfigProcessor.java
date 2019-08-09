package com.jsorrell.blockowater.common.config;

import com.jsorrell.blockowater.BlockOWater;
import com.jsorrell.blockowater.Values;
import com.jsorrell.blockowater.common.network.BlockOWaterPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

public class ConfigProcessor {
  public static final ConfigProcessor INSTANCE = new ConfigProcessor();

  /* Events */
  @SubscribeEvent
  @SuppressWarnings("unused")
  public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
    BlockOWater.logger.info("Sending server configs to client");
    BlockOWaterPacketHandler.INSTANCE.sendTo(new PacketConfigSync(), (EntityPlayerMP) event.player);
  }

  @SubscribeEvent
  @SuppressWarnings("unused")
  public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (!event.getModID().equals(Values.MOD_ID)) return;

    // If we're a multiplayer client, we should not sync to ConfigSettings, which contains the server config.
    if (FMLCommonHandler.instance().getEffectiveSide() != Side.CLIENT || !event.isWorldRunning() || BlockOWater.proxy.isClientSinglePlayer()) {
      ConfigManager.sync(Values.MOD_ID, Config.Type.INSTANCE);
    } else {
      // HACK: Save and restore server settings
      Map<String, Object> map = ConfigSettings.getSettingsMap(true);
      ConfigManager.sync(Values.MOD_ID, Config.Type.INSTANCE);
      ConfigSettings.loadSettingsMap(map);
      ConfigManager.sync(Values.MOD_ID, Config.Type.INSTANCE);
    }
  }
}
