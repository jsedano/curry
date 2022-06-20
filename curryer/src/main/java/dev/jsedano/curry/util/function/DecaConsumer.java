package dev.jsedano.curry.util.function;

@FunctionalInterface
public interface DecaConsumer<A, B, C, D, E, F, G, H, I, J, R> {

  R apply(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j);
}
