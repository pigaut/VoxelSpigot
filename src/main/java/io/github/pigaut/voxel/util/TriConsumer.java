package io.github.pigaut.voxel.util;

@FunctionalInterface
public interface TriConsumer<T, K, V> {

    void accept(T t, K k, V v);

}
