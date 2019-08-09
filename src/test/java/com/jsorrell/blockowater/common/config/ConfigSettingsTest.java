package com.jsorrell.blockowater.common.config;

import org.apache.commons.lang3.ClassUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

public class ConfigSettingsTest {

  private void checkAllFields(Class clazz) {
    for (Field field : clazz.getDeclaredFields()) {
      // Ensure not primitive type
      Assertions.assertFalse(field.getType().isPrimitive(), "field \"" + field.getName() + "\" is primitive.");
      if (!ClassUtils.isPrimitiveWrapper(field.getType())) {
        checkAllFields(field.getType());
      }
    }
  }

  @Test
  void testSettingsValues() {
    checkAllFields(ConfigSettings.class);
  }

  @Test
  void testSettingsMap() {
    ConfigSettings.workingConditions.worksInNether = true;
    ConfigSettings.waterGeneration.generationTimer = 17;
    ConfigSettings.pushConfig.canPush = true;
    ConfigSettings.client.tooltips.enabled = true;

    Map map = ConfigSettings.getSettingsMap(false);
    Assertions.assertEquals(17, map.get("waterGeneration.generationTimer"));
    Assertions.assertEquals(true, map.get("client.tooltips.enabled"));
    Assertions.assertNull(map.get(""));

    Map map2 = ConfigSettings.getSettingsMap(true);
    Assertions.assertEquals(17, map2.get("waterGeneration.generationTimer"));
    Assertions.assertNull(map2.get("client.tooltips.enabled"));
    Assertions.assertNull(map2.get(""));

    // Load
    ConfigSettings.waterGeneration.generationTimer = 1224;
    ConfigSettings.pushConfig.pushAmount = 1234;
    ConfigSettings.client.tooltips.enabled = true;

    Map<String, Object> mapb1 = ConfigSettings.getSettingsMap(true);

    ConfigSettings.waterGeneration.generationTimer = 4221;
    ConfigSettings.pushConfig.pushAmount = 4321;
    ConfigSettings.client.tooltips.enabled = false;

    ConfigSettings.loadSettingsMap(mapb1);

    Assertions.assertEquals(1224, ConfigSettings.waterGeneration.generationTimer);
    Assertions.assertEquals(1234, ConfigSettings.pushConfig.pushAmount);
    Assertions.assertEquals(false, ConfigSettings.client.tooltips.enabled);
  }
}
