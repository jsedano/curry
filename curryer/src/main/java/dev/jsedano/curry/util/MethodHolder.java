package dev.jsedano.curry.util;

import java.util.List;

public class MethodHolder {

  private String name;
  private List<String> parameters;
  private String returnType;
  private Boolean isConstructor;

  private FunctionType functionType;

  public MethodHolder(
      String name, Boolean isConstructor, List<String> parameters, String returnType) {
    this.name = name;
    this.isConstructor = isConstructor;
    this.parameters = parameters;
    this.returnType = returnType;
    this.functionType =
        this.returnType.equals("void") ? FunctionType.CONSUMER : FunctionType.FUNCTION;
  }

  public String getName() {
    return name;
  }

  public List<String> getParameters() {
    return parameters;
  }

  public String getReturnType() {
    return returnType;
  }

  public Boolean getConstructor() {
    return isConstructor;
  }

  public FunctionType getFunctionType() {
    return functionType;
  }
}
