package dev.jsedano.curry.tests;

import dev.jsedano.curry.annotation.Curry;
import java.awt.geom.Point2D;
import java.util.List;

public class Currucucu {
  private String s;
  private int i;

  public Currucucu() {}

  @Curry
  public Currucucu(String s, Integer i) {
    this.s = s;
    this.i = i;
  }

  @Curry
  public List<String> addToList(String s, int i, String g, List<String> list) {
    list.add(s);
    list.add(i + "");
    list.add(g);
    return list;
  }

  @Curry
  public void addToList(String s, List<String> list) {
    list.add(s);
  }

  @Override
  public String toString() {
    return s + i;
  }

  @Curry
  public static String concatenate2(String i, Integer j, Character d) {
    return i + j + d;
  }

  @Curry
  public static String[] concatenate(String i, Integer j, Character d, Something s) {
    return new String[] {i + j + d + s.toString()};
  }

  @Curry
  public static Void concatenate21(String i[], Integer j, Character d) {
    return (Void) null;
  }

  class Something {
    Point2D.Double s;
  }
}
