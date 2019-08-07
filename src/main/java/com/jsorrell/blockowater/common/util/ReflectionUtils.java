package com.jsorrell.blockowater.common.util;

import com.jsorrell.blockowater.BlockOWater;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class ReflectionUtils {
  @Nullable
  public static Field getField(Class clazz, String fieldName) {
    try {
      return clazz.getField(fieldName);
    } catch (NoSuchFieldException e) {
      BlockOWater.logger.warn("Field " + fieldName + " not found in " + clazz.getName());
    }
    return null;
  }

  public static Object resolvePublicField(Field field, @Nullable Object obj) {
    try {
      return field.get(obj);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  public static void setPublicField(Field field, @Nullable Object obj, Object fieldValue) {
    try {
      field.set(obj, fieldValue);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }
}
