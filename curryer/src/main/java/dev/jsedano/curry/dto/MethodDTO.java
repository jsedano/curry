package dev.jsedano.curry.dto;

import java.util.List;

public class MethodDTO {

  private String name;
  private List<String> parameters;
  private String returnType;
  private Boolean isConstructor;

  public MethodDTO(String name, Boolean isConstructor, List<String> parameters, String returnType) {
    this.name = name;
    this.isConstructor = isConstructor;
    this.parameters = parameters;
    this.returnType = returnType;
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
}
