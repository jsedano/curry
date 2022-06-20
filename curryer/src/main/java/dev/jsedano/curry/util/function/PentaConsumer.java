package dev.jsedano.curry.util.function;

@FunctionalInterface
public interface PentaConsumer<A, B, C, D, E, R> {

  R apply(A a, B b, C c, D d, E e);
}
