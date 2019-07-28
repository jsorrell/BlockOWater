package com.jsorrell.blockowater;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Values.MOD_ID)
public class BlockOWater {
  private static final Logger LOGGER = LogManager.getLogger();

  public BlockOWater() {
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);
  }

  private void setup(final FMLCommonSetupEvent event) {
  }
}
