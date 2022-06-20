package dev.jsedano.curry.util.function;

@FunctionalInterface
public interface HexaConsumer<A, B, C, D, E, F, R> {

  R apply(A a, B b, C c, D d, E e, F f);
}
