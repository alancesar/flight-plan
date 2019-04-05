package org.alancesar.repository;

import java.util.List;
import java.util.function.Function;

public interface Repository<T, K> {

    void write(T input, Function<T, K> mapper);
    List<T> readAll(Function<K, T> mapper);
}
