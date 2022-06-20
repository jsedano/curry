package dev.jsedano.curry.processor;

import com.google.auto.service.AutoService;
import dev.jsedano.curry.dto.MethodDTO;
import dev.jsedano.curry.util.FunctionSelector;
import dev.jsedano.curry.util.PrimitiveWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("dev.jsedano.curry.annotation.Curry")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class CurryProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    for (TypeElement annotation : annotations) {

      Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

      // lets make sure around here that only methods with more than one parameter are annotated
      Map<Boolean, List<Element>> annotatedMethods =
          annotatedElements.stream().collect(Collectors.partitioningBy(element -> (true)));

      List<Element> setters = annotatedMethods.get(true);

      if (setters.isEmpty()) {
        continue;
      }

      String className =
          ((TypeElement) setters.get(0).getEnclosingElement()).getQualifiedName().toString();

      List<MethodDTO> methodList =
          setters.stream()
              .map(
                  setter ->
                      new MethodDTO(
                          setter.getSimpleName().toString(),
                          setter.getKind() == ElementKind.CONSTRUCTOR,
                          ((ExecutableType) setter.asType())
                              .getParameterTypes().stream()
                                  .map(TypeMirror::toString)
                                  .map(PrimitiveWrapper::wrapIfPrimitive)
                                  .toList(),
                          setter.getKind() == ElementKind.CONSTRUCTOR
                              ? className
                              : PrimitiveWrapper.wrapIfPrimitive(
                                  ((ExecutableType) setter.asType()).getReturnType().toString())))
              .toList();

      try {
        writeBuilderFile(className, methodList);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return true;
  }

  private void writeBuilderFile(String className, List<MethodDTO> methodList) throws IOException {

    String packageName = null;
    int lastDot = className.lastIndexOf('.');
    if (lastDot > 0) {
      packageName = className.substring(0, lastDot);
    }

    String simpleClassName = className.substring(lastDot + 1);
    String builderClassName = className + "Curryer";
    String builderSimpleClassName = builderClassName.substring(lastDot + 1);

    JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(builderClassName);
    try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {

      if (packageName != null) {
        out.print("package ");
        out.print(packageName);
        out.println(";");
        out.println();
      }
      out.print("public class ");
      out.print(builderSimpleClassName);
      out.println(" {");
      out.println();

      methodList.forEach(
          setter -> {
            String returnType = setter.getReturnType();
            List<String> argumentsType = setter.getParameters();

            out.print("    public static ");
            out.print(getReturnType(argumentsType, returnType, 0));
            out.print(" ");
            out.print(setter.getConstructor() ? "curry" : setter.getName());

            out.print("(");
            out.print(getArgument(argumentsType, returnType, 0));

            out.println(") {");
            out.print(getBody3(argumentsType, 0));
            out.println("}");
            out.println();
          });

      out.println("}");
    }
  }

  private String getReturnType(List<String> methods, String returnType, int i) {
    if (i == methods.size()) {
      return returnType;
    }
    return String.format(
        "%s<%s,%s>",
        FunctionSelector.getFunction(1), methods.get(i), getReturnType(methods, returnType, i + 1));
  }

  private String getArgument(List<String> methods, String returnType, int i) {
    if (i == 0) {
      return String.format(
          "%s<%s,%s",
          FunctionSelector.getFunction(methods.size()),
          methods.get(i),
          getArgument(methods, returnType, i + 1));
    }
    if (i == methods.size()) {
      return String.format("%s> function", returnType);
    }
    return String.format("%s,%s", methods.get(i), getArgument(methods, returnType, i + 1));
  }

  private String getBody(List<String> methods, int i) {
    if (i == methods.size()) {
      return "";
    }
    return String.format("v%d -> %s", i, getBody(methods, i + 1));
  }

  private String getBody2(List<String> methods, int i) {
    if (i == methods.size() - 1) {
      return String.format("v%d", i);
    }
    return String.format("v%d,%s", i, getBody2(methods, i + 1));
  }

  private String getBody3(List<String> methods, int i) {
    return String.format(
        "return %s function.apply(%s);", getBody(methods, 0), getBody2(methods, 0));
  }

  /*
    public static Function<Integer, Function<Integer, Integer>> toCurry(
      BiFunction<Integer, Integer, Integer> function) {
    return v -> v1 -> function.apply(v, v1);
  }
     */
}
