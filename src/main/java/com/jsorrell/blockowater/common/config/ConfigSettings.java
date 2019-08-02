package com.jsorrell.blockowater.common.config;

import com.jsorrell.blockowater.Values;
import net.minecraftforge.common.config.Config;

@Config(modid = Values.MOD_ID, type = Config.Type.INSTANCE)
public final class ConfigSettings {
  @Config.Name("Generation")
  @Config.Comment("Config for how a Block O' Water generates water.")
  public static WaterGeneration waterGeneration = new WaterGeneration();

  @Config.Name("Pushing")
  @Config.Comment("Config for how a Block O' Water pushes to adjacent blocks.")
  public static PushConfig pushConfig = new PushConfig();

  @Config.Name("Conditions")
  @Config.Comment("Config for the conditions a Block O' Water works in.")
  public static WorkingConditions workingConditions = new WorkingConditions();

  /* Categories */
  public static class WaterGeneration {
    @Config.Name("Generation Amount")
    @Config.RangeInt(min = 1)
    @Config.Comment("Default: 2147483647")
    public int generationAmount = Integer.MAX_VALUE;

    @Config.Name("Ticks per Generation")
    @Config.RangeInt(min = 1)
    @Config.Comment("Default: 1")
    public int generationTimer = 1;
  }

  public static class PushConfig {
    @Config.Name("Can Push")
    @Config.Comment({
      "If this is false, water must be extracted from a Block O' Water.",
      "Must be true for all other Pushing options to apply.",
      "Default: true"
    })
    public boolean canPush = true;

    @Config.Name("Push Amount")
    @Config.RangeInt(min = 1)
    @Config.Comment("Default: 2147483647")
    public int pushAmount = Integer.MAX_VALUE;

    @Config.Name("Ticks per Push")
    @Config.RangeInt(min = 1)
    @Config.Comment("Default: 10")
    public int pushTimer = 10;
  }

  public static class WorkingConditions {
    @Config.Name("Requires Water Biome")
    @Config.Comment({
      "Includes Ocean and River Biomes",
      "Default: false"
    })
    public boolean requiresWaterBiome = false;

    @Config.Name("Works in Nether")
    @Config.Comment({
      "Ignored if Requires Water Biome",
      "Default: true"
    })
    public boolean worksInNether = true;
  }
}
