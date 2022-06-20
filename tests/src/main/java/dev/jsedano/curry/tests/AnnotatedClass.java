package dev.jsedano.curry.tests;

import dev.jsedano.curry.annotation.Curry;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class AnnotatedClass {

  private boolean aBoolean;
  private List<String> aStringList;
  private int aNumber;

  private char aChar;

  private float aFloat;

  private char[] aCharArray;
  private BigDecimal aBigDecimal;
  private LocalDateTime aLocalDateTime;

  private AnEnum anEnum;
  private AnnotatedClass anAnnotatedClass;

  @Curry
  public AnnotatedClass(
      boolean aBoolean,
      List<String> aStringList,
      int aNumber,
      char aChar,
      float aFloat,
      char[] aCharArray,
      BigDecimal aBigDecimal,
      LocalDateTime aLocalDateTime,
      AnEnum anEnum,
      AnnotatedClass anAnnotatedClass) {
    this.aBoolean = aBoolean;
    this.aStringList = aStringList;
    this.aNumber = aNumber;
    this.aChar = aChar;
    this.aFloat = aFloat;
    this.aCharArray = aCharArray;
    this.aBigDecimal = aBigDecimal;
    this.aLocalDateTime = aLocalDateTime;
    this.anEnum = anEnum;
    this.anAnnotatedClass = anAnnotatedClass;
  }

  @Curry
  public AnnotatedClass(
      boolean aBoolean, List<String> aStringList, int aNumber, char aChar, float aFloat) {
    this.aBoolean = aBoolean;
    this.aStringList = aStringList;
    this.aNumber = aNumber;
    this.aChar = aChar;
    this.aFloat = aFloat;
  }

  @Curry
  public void aVoidMethod(String string, Integer integer) {
    for (int i = 0; i < integer; i++) {
      aStringList.add(string);
    }
  }

  @Curry
  public static int[] aStaticMethod(int i, int j) {
    return new int[] {i, j};
  }

  public List<String> getaStringList() {
    return aStringList;
  }

  @Override
  public String toString() {
    return "AnnotatedClass{"
        + "aBoolean="
        + aBoolean
        + ", aStringList="
        + aStringList
        + ", aNumber="
        + aNumber
        + ", aChar="
        + aChar
        + ", aFloat="
        + aFloat
        + ", aCharArray="
        + Arrays.toString(aCharArray)
        + ", aBigDecimal="
        + aBigDecimal
        + ", aLocalDateTime="
        + aLocalDateTime
        + ", anEnum="
        + anEnum
        + ", anAnnotatedClass="
        + anAnnotatedClass
        + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AnnotatedClass that = (AnnotatedClass) o;

    if (aBoolean != that.aBoolean) return false;
    if (aNumber != that.aNumber) return false;
    if (aChar != that.aChar) return false;
    if (Float.compare(that.aFloat, aFloat) != 0) return false;
    if (!Objects.equals(aStringList, that.aStringList)) return false;
    if (!Arrays.equals(aCharArray, that.aCharArray)) return false;
    if (!Objects.equals(aBigDecimal, that.aBigDecimal)) return false;
    if (!Objects.equals(aLocalDateTime, that.aLocalDateTime)) return false;
    if (anEnum != that.anEnum) return false;
    return Objects.equals(anAnnotatedClass, that.anAnnotatedClass);
  }

  @Override
  public int hashCode() {
    int result = (aBoolean ? 1 : 0);
    result = 31 * result + (aStringList != null ? aStringList.hashCode() : 0);
    result = 31 * result + aNumber;
    result = 31 * result + (int) aChar;
    result = 31 * result + (aFloat != +0.0f ? Float.floatToIntBits(aFloat) : 0);
    result = 31 * result + Arrays.hashCode(aCharArray);
    result = 31 * result + (aBigDecimal != null ? aBigDecimal.hashCode() : 0);
    result = 31 * result + (aLocalDateTime != null ? aLocalDateTime.hashCode() : 0);
    result = 31 * result + (anEnum != null ? anEnum.hashCode() : 0);
    result = 31 * result + (anAnnotatedClass != null ? anAnnotatedClass.hashCode() : 0);
    return result;
  }
}
