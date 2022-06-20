package dev.jsedano.curry.util.function;

@FunctionalInterface
public interface OctaConsumer<A, B, C, D, E, F, G, H, R> {

  R apply(A a, B b, C c, D d, E e, F f, G g, H h);
}
