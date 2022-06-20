package dev.jsedano.curry.tests;

import org.junit.jupiter.api.Test;

public class CurrucucuTest {

  @Test
  public void test() {

    var factory = CurrucucuCurryer.curry(Currucucu::new);
    var holaFactory = factory.apply("hola");
    var a = holaFactory.apply(10);
    System.out.println(a.toString());
    var concat2 = CurrucucuCurryer.concatenate2(Currucucu::concatenate2);
    System.out.println(concat2.apply("hola").apply(1).apply('c'));
  }
}
