package dev.jsedano.curry.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedList;
import org.junit.jupiter.api.Test;

public class AnnotatedClassTest {

  @Test
  public void generatedDecaConstructorTest() {
    LocalDateTime localDateTime = LocalDateTime.now();
    assertEquals(
        new AnnotatedClass(
            true,
            Arrays.asList("aString"),
            1,
            'c',
            2.4f,
            "hola".toCharArray(),
            BigDecimal.ONE,
            localDateTime,
            AnEnum.FIRST,
            null),
        AnnotatedClassCurryer.decaConstructor(AnnotatedClass::new)
            .apply(true)
            .apply(Arrays.asList("aString"))
            .apply(1)
            .apply('c')
            .apply(2.4f)
            .apply("hola".toCharArray())
            .apply(BigDecimal.ONE)
            .apply(localDateTime)
            .apply(AnEnum.FIRST)
            .apply(null));
  }

  @Test
  public void generatedPentaConstructorTest() {
    assertEquals(
        new AnnotatedClass(true, Arrays.asList("aString"), 1, 'c', 2.4f),
        AnnotatedClassCurryer.pentaConstructor(AnnotatedClass::new)
            .apply(true)
            .apply(Arrays.asList("aString"))
            .apply(1)
            .apply('c')
            .apply(2.4f));
  }

  @Test
  public void voidMethodTest() {
    var list = new LinkedList<String>();
    var annotatedClass =
        AnnotatedClassCurryer.pentaConstructor(AnnotatedClass::new)
            .apply(true)
            .apply(list)
            .apply(1)
            .apply('c')
            .apply(2.4f);

    var voidMethod = AnnotatedClassCurryer.aVoidMethod(annotatedClass::aVoidMethod);

    assertEquals(0, annotatedClass.getaStringList().size());

    var voidMethodWithPartialApplication = voidMethod.apply("anotherString");

    assertEquals(0, annotatedClass.getaStringList().size());

    voidMethodWithPartialApplication.accept(5);

    assertEquals(5, annotatedClass.getaStringList().size());

    voidMethodWithPartialApplication.accept(5);

    assertEquals(10, annotatedClass.getaStringList().size());

    for (String s : annotatedClass.getaStringList()) {
      assertEquals("anotherString", s);
    }
  }

  @Test
  public void staticMethodTest() {
    var staticMethod = AnnotatedClassCurryer.aStaticMethod(AnnotatedClass::aStaticMethod);

    var expected = new int[] {0, 1};
    var result = staticMethod.apply(0).apply(1);
    assertEquals(expected[0], result[0]);
    assertEquals(expected[1], result[1]);
  }
}
