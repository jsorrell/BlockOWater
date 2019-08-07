package com.jsorrell.blockowater.client;

import com.jsorrell.blockowater.common.config.ConfigSettings;
import com.jsorrell.blockowater.common.core.BlockOWaterBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class ItemTooltipHandler {
  public static final ItemTooltipHandler INSTANCE = new ItemTooltipHandler();

  @SubscribeEvent
  @SuppressWarnings("unused")
  public void ItemTooltipEvent(ItemTooltipEvent event) {
    if (event.getItemStack().getItem() == BlockOWaterBlocks.blockOWaterItemBlock) {
      if (isPressingSneak()) {
        if (ConfigSettings.pushConfig.canPush) {
          event.getToolTip().add(TextFormatting.GREEN + I18n.format("tooltip.blockOWater.canPush"));
        } else {
          event.getToolTip().add(TextFormatting.RED + I18n.format("tooltip.blockOWater.cantPush"));
        }
        if (ConfigSettings.workingConditions.requiresWaterBiome) {
          event.getToolTip().add(TextFormatting.LIGHT_PURPLE + I18n.format("tooltip.blockOWater.requiresWaterBiome"));
        } else if (!ConfigSettings.workingConditions.worksInNether) {
          event.getToolTip().add(TextFormatting.LIGHT_PURPLE + I18n.format("tooltip.blockOWater.netherDisabled"));
        }
      }
    }
  }

  private boolean isPressingSneak() {
    // keyBindSneak.isKeyDown() doesn't seem to work for some reason
    return Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode());
  }
}
