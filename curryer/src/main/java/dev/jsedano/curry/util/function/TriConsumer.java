package dev.jsedano.curry.util.function;

@FunctionalInterface
public interface TriConsumer<A, B, C, R> {

  R apply(A a, B b, C c);
}
