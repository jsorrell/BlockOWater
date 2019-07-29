package com.jsorrell.blockowater;

import com.jsorrell.blockowater.client.ClientProxy;
import com.jsorrell.blockowater.common.ServerProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Values.MOD_ID)
public class BlockOWater {
  private static final Logger LOGGER = LogManager.getLogger();
  public static BlockOWater instance;
  public static ServerProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);

  public BlockOWater() {
    instance = this;
    FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);
  }

  private void setup(final FMLCommonSetupEvent event) {
  }
}
