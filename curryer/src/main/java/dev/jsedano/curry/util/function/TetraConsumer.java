package dev.jsedano.curry.util.function;

@FunctionalInterface
public interface TetraConsumer<A, B, C, D> {

  void accept(A a, B b, C c, D d);
}
