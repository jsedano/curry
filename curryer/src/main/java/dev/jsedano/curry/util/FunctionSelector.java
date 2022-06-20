package dev.jsedano.curry.util;

import dev.jsedano.curry.util.function.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class FunctionSelector {

  private static final String[] classArray = {
    Function.class.getName(),
    BiFunction.class.getName(),
    TriFunction.class.getName(),
    TetraFunction.class.getName(),
    PentaFunction.class.getName(),
    HexaFunction.class.getName(),
    HeptaFunction.class.getName(),
    OctaFunction.class.getName(),
    NonaFunction.class.getName(),
    DecaFunction.class.getName()
  };

  public static String getFunction(int parameters) {
    return classArray[parameters - 1];
  }
}
