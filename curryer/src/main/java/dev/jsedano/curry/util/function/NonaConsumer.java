package dev.jsedano.curry.util.function;

@FunctionalInterface
public interface NonaConsumer<A, B, C, D, E, F, G, H, I, R> {

  R apply(A a, B b, C c, D d, E e, F f, G g, H h, I i);
}
