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

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getParameters() {
    return parameters;
  }

  public void setParameters(List<String> parameters) {
    this.parameters = parameters;
  }

  public String getReturnType() {
    return returnType;
  }

  public void setReturnType(String returnType) {
    this.returnType = returnType;
  }

  public Boolean getConstructor() {
    return isConstructor;
  }

  public void setConstructor(Boolean constructor) {
    isConstructor = constructor;
  }
}
