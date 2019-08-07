package com.jsorrell.blockowater.common.config;

import com.jsorrell.blockowater.Values;
import com.jsorrell.blockowater.common.config.annotations.NoSync;
import com.jsorrell.blockowater.common.util.ReflectionUtils;
import net.minecraftforge.common.config.Config;
import org.apache.commons.lang3.ClassUtils;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Config(modid = Values.MOD_ID, type = Config.Type.INSTANCE, category = "")
public final class ConfigSettings implements Serializable {
  @Config.Name("Generation")
  @Config.Comment("Config for how a Block O' Water generates water.")
  @Config.LangKey("config.generation")
  public static WaterGeneration waterGeneration = new WaterGeneration();

  @Config.Name("Pushing")
  @Config.Comment("Config for how a Block O' Water pushes to adjacent blocks.")
  @Config.LangKey("config.pushing")
  public static PushConfig pushConfig = new PushConfig();

  @Config.Name("Conditions")
  @Config.Comment("Config for the conditions a Block O' Water works in.")
  @Config.LangKey("config.conditions")
  public static WorkingConditions workingConditions = new WorkingConditions();

  /* Categories */
  public static class WaterGeneration {
    @Config.Name("Generation Amount")
    @Config.RangeInt(min = 1)
    @Config.Comment("Default: 2147483647")
    public Integer generationAmount = Integer.MAX_VALUE;

    @Config.Name("Ticks per Generation")
    @Config.RangeInt(min = 1)
    @Config.Comment("Default: 1")
    public Integer generationTimer = 1;
  }

  public static class PushConfig {
    @Config.Name("Can Push")
    @Config.Comment({
      "If this is false, water must be extracted from a Block O' Water.",
      "Must be true for all other Pushing options to apply.",
      "Default: true"
    })
    public Boolean canPush = true;

    @Config.Name("Push Amount")
    @Config.RangeInt(min = 1)
    @Config.Comment("Default: 2147483647")
    public Integer pushAmount = Integer.MAX_VALUE;

    @Config.Name("Ticks per Push")
    @Config.RangeInt(min = 1)
    @Config.Comment("Default: 10")
    public Integer pushTimer = 10;
  }

  public static class WorkingConditions {
    @Config.Name("Requires Water Biome")
    @Config.Comment({
      "Includes Ocean and River Biomes",
      "Default: false"
    })
    public Boolean requiresWaterBiome = false;

    @Config.Name("Works in Nether")
    @Config.Comment({
      "Ignored if Requires Water Biome",
      "Default: true"
    })
    public Boolean worksInNether = true;
  }

  /* Serialization */

  public static HashMap<String, Serializable> getSettingsMap(boolean excludeNoSync) {
    HashMap<String, Serializable> map = new HashMap<>();
    addObjectToMap(map, ConfigSettings.class, null, "", excludeNoSync);
    return map;
  }

  private static void addObjectToMap(Map<String, Serializable> map, Class clazz, @Nullable Object obj, String prefix, boolean excludeNoSync) {
    for (Field field : clazz.getDeclaredFields()) {
      // Don't serialize fields with NoSync annotation when excludeNoSync true
      if (excludeNoSync && field.isAnnotationPresent(NoSync.class)) continue;

      String fieldName = prefix + field.getName();
      Object fieldValue = ReflectionUtils.resolvePublicField(field, obj);

      if (ClassUtils.isPrimitiveWrapper(field.getType())) {
        map.put(fieldName, (Serializable) fieldValue);
      } else {
        addObjectToMap(map, field.getType(), fieldValue, fieldName + ".", excludeNoSync);
      }
    }
  }

  public static void loadFieldMap(Map<String, Object> map) {
    for (String fieldName : map.keySet()) {
      loadField(ConfigSettings.class, fieldName, null, map.get(fieldName));
    }
  }

  private static <T> void loadField(Class clazz, String fieldName, @Nullable T obj, Object fieldValue) {
    if (!fieldName.contains(".")) {
      Field field = ReflectionUtils.getField(clazz, fieldName);
      if (field == null) return;
      ReflectionUtils.setPublicField(field, obj, fieldValue);
    } else {
      String[] splits = fieldName.split("\\.", 2);
      Field field = ReflectionUtils.getField(clazz, splits[0]);
      if (field == null) return;
      loadField(field.getType(), splits[1], ReflectionUtils.resolvePublicField(field, obj), fieldValue);
    }
  }
}
