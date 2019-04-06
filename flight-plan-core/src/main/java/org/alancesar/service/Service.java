package org.alancesar.service;

import java.util.List;

public interface Service<T> {

    List<T> getAll();
    void save(T input);
}
