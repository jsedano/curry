package dev.jsedano.curry.processor;

import com.google.auto.service.AutoService;
import dev.jsedano.curry.util.FunctionSelector;
import dev.jsedano.curry.util.FunctionType;
import dev.jsedano.curry.util.MethodHolder;
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
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("dev.jsedano.curry.annotation.Curry")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
@AutoService(Processor.class)
public class CurryProcessor extends AbstractProcessor {

  @Override
  public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

    for (TypeElement annotation : annotations) {

      Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

      Map<Boolean, List<Element>> annotatedMethods =
          annotatedElements.stream()
              .collect(
                  Collectors.partitioningBy(
                      element ->
                          ((ExecutableType) element.asType()).getParameterTypes().size() > 1
                              && ((ExecutableType) element.asType()).getParameterTypes().size()
                                  < 11));

      List<Element> methodsToCurry = annotatedMethods.get(true);
      List<Element> otherMethods = annotatedMethods.get(false);

      otherMethods.stream()
          .forEach(
              e ->
                  processingEnv
                      .getMessager()
                      .printMessage(
                          Diagnostic.Kind.MANDATORY_WARNING,
                          "incorrect number of parameters, allowed only between 2 and 1O, will not generate code for this one",
                          e));

      if (methodsToCurry.isEmpty()) {
        continue;
      }

      String className =
          ((TypeElement) methodsToCurry.get(0).getEnclosingElement()).getQualifiedName().toString();

      List<MethodHolder> methodList =
          methodsToCurry.stream()
              .map(
                  element ->
                      new MethodHolder(
                          element.getSimpleName().toString(),
                          element.getKind() == ElementKind.CONSTRUCTOR,
                          ((ExecutableType) element.asType())
                              .getParameterTypes().stream()
                                  .map(TypeMirror::toString)
                                  .map(PrimitiveWrapper::wrapIfPrimitive)
                                  .toList(),
                          element.getKind() == ElementKind.CONSTRUCTOR
                              ? className
                              : PrimitiveWrapper.wrapIfPrimitive(
                                  ((ExecutableType) element.asType()).getReturnType().toString())))
              .toList();

      try {
        writeBuilderFile(className, methodList);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return true;
  }

  private void writeBuilderFile(String className, List<MethodHolder> methodList)
      throws IOException {

    String packageName = null;
    int lastDot = className.lastIndexOf('.');
    if (lastDot > 0) {
      packageName = className.substring(0, lastDot);
    }

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
      out.print("public final class ");
      out.print(builderSimpleClassName);
      out.println(" {");
      out.println();

      methodList.forEach(
          method -> {
            out.print("    public static ");
            out.print(
                getReturnType(
                    method.getParameters(), method.getReturnType(), method.getFunctionType()));
            out.print(" ");
            out.print(
                method.getConstructor()
                    ? FunctionSelector.getFunctionShortName(method.getParameters().size())
                        + "Constructor"
                    : method.getName());

            out.print("(");
            out.print(
                getArgument(
                    method.getParameters(), method.getReturnType(), method.getFunctionType()));

            out.println(") {");
            out.print("        ");
            out.println(getBody(method.getParameters().size(), method.getFunctionType()));
            out.println("}");
            out.println();
          });

      out.println("}");
    }
  }

  private String getReturnType(List<String> methods, String returnType, FunctionType functionType) {
    return getReturnType(methods, returnType, functionType, 0);
  }

  private String getReturnType(
      List<String> methods, String returnType, FunctionType functionType, int i) {
    if (functionType == FunctionType.CONSUMER && i == methods.size() - 1) {
      return String.format(
          "%s<%s>", FunctionSelector.getFunction(1, FunctionType.CONSUMER), methods.get(i));
    }
    if (i == methods.size()) {
      return returnType;
    }
    return String.format(
        "%s<%s,%s>",
        FunctionSelector.getFunction(1, FunctionType.FUNCTION),
        methods.get(i),
        getReturnType(methods, returnType, functionType, i + 1));
  }

  private String getArgument(List<String> methods, String returnType, FunctionType functionType) {
    return getArgument(methods, returnType, functionType, 0);
  }

  private String getArgument(
      List<String> methods, String returnType, FunctionType functionType, int i) {
    if (i == 0) {
      return String.format(
          "%s<%s,%s",
          FunctionSelector.getFunction(methods.size(), functionType),
          methods.get(i),
          getArgument(methods, returnType, functionType, i + 1));
    }
    if (functionType == FunctionType.CONSUMER && i == methods.size() - 1) {
      return String.format("%s> function", methods.get(i));
    }
    if (i == methods.size()) {
      return String.format("%s> function", returnType);
    }
    return String.format(
        "%s,%s", methods.get(i), getArgument(methods, returnType, functionType, i + 1));
  }

  private String getBody(int parameterCount, FunctionType functionType) {
    return String.format(
        functionType == FunctionType.FUNCTION
            ? "return %s function.apply(%s);"
            : "return %s function.accept(%s);",
        getLambdaArrows(parameterCount, 0),
        getFunctionCallParameters(parameterCount, 0, functionType));
  }

  private String getLambdaArrows(int parameterCount, int i) {
    if (i == parameterCount) {
      return "";
    }
    return String.format("v%d->%s", i, getLambdaArrows(parameterCount, i + 1));
  }

  private String getFunctionCallParameters(int parameterCount, int i, FunctionType functionType) {
    if (i == parameterCount - 1) {
      return String.format("v%d", i);
    }
    return String.format(
        "v%d,%s", i, getFunctionCallParameters(parameterCount, i + 1, functionType));
  }
}
