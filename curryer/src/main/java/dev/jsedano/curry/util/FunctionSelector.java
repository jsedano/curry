package dev.jsedano.curry.util;

import dev.jsedano.curry.util.function.*;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public final class FunctionSelector {

  private static final String[] functionArray = {
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

  private static final String[] consumerArray = {
    Consumer.class.getName(),
    BiConsumer.class.getName(),
    TriConsumer.class.getName(),
    TetraConsumer.class.getName(),
    PentaConsumer.class.getName(),
    HexaConsumer.class.getName(),
    HeptaConsumer.class.getName(),
    OctaConsumer.class.getName(),
    NonaConsumer.class.getName(),
    DecaConsumer.class.getName()
  };

  public static String getFunction(int parameters, FunctionType functionType) {
    return functionType == FunctionType.FUNCTION
        ? functionArray[parameters - 1]
        : consumerArray[parameters - 1];
  }

  public static String getFunctionShortName(int parameters) {
    String functionName = getFunction(parameters, FunctionType.FUNCTION);
    return functionName
        .substring(functionName.lastIndexOf('.') + 1, functionName.lastIndexOf('F'))
        .toLowerCase();
  }
}
