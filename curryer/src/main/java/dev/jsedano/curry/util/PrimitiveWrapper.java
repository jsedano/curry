package dev.jsedano.curry.util;

import java.util.HashMap;
import java.util.Map;

public final class PrimitiveWrapper {

  private static final Map<String, String> primitiveToWrapper =
      new HashMap<String, String>() {
        {
          put("boolean", Boolean.class.getName());
          put("byte", Byte.class.getName());
          put("char", Character.class.getName());
          put("double", Double.class.getName());
          put("float", Float.class.getName());
          put("int", Integer.class.getName());
          put("long", Long.class.getName());
          put("short", Short.class.getName());
        }
      };

  public static String wrapIfPrimitive(String type) {
    if (primitiveToWrapper.containsKey(type)) {
      return primitiveToWrapper.get(type);
    }

    return type;
  }
}
