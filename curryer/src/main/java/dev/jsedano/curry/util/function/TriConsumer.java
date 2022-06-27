package dev.jsedano.curry.util.function;

@FunctionalInterface
public interface TriConsumer<A, B, C> {

  void accept(A a, B b, C c);
}
