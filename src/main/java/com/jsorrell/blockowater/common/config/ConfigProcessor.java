package com.jsorrell.blockowater.common.config;

import com.jsorrell.blockowater.BlockOWater;
import com.jsorrell.blockowater.common.network.BlockOWaterPacketHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Map;

public class ConfigProcessor {
  public static final ConfigProcessor INSTANCE = new ConfigProcessor();

  // Don't contain nosync entries
  public Map<String, Object> clientConfig;
  public Map<String, Object> serverConfig;

  /* Events */
  @SubscribeEvent
  @SuppressWarnings("unused")
  public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
    // Only have to send this on physical server
    if (FMLCommonHandler.instance().getSide() == Side.SERVER) {
      BlockOWater.logger.info("Sending server configs to client");
      BlockOWaterPacketHandler.INSTANCE.sendTo(new PacketConfigSync(), (EntityPlayerMP) event.player);
    }
  }

  @SubscribeEvent
  @SuppressWarnings("unused")
  public void onPlayerLogout(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
    BlockOWater.logger.info("Restoring client config");
    ConfigSettings.loadSettingsMap(clientConfig);
    INSTANCE.serverConfig = null;
    INSTANCE.clientConfig = null;
  }

  @SubscribeEvent
  @SuppressWarnings("unused")
  public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
    if (!event.getModID().equals(BlockOWater.MODID)) return;

    // If we're the logical client, in game, and not in single player, we need to sync our settings with the server
    if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT && event.isWorldRunning() && !BlockOWater.proxy.isClientSinglePlayer()) {
      // TODO Override the gui and disable the ability to change these settings at all
      // TODO Implement custom config to remove these hacks and be able to show (uneditable) server config in config menu
      // HACK: Save and restore server settings. Without access to the Configuration object, all we can do is sync.
      // Get changes to client's config
      ConfigManager.sync(BlockOWater.MODID, Config.Type.INSTANCE);
      // Reset changes to client's server config
      ConfigSettings.loadSettingsMap(INSTANCE.clientConfig);
      ConfigManager.sync(BlockOWater.MODID, Config.Type.INSTANCE);
      // Reload server settings
      ConfigSettings.loadSettingsMap(INSTANCE.serverConfig);
    } else {
      ConfigManager.sync(BlockOWater.MODID, Config.Type.INSTANCE);
    }
  }
}
