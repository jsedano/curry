package dev.jsedano.curry.util.function;

@FunctionalInterface
public interface TetraConsumer<A, B, C, D, R> {

  R apply(A a, B b, C c, D d);
}
