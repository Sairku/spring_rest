package com.example.spring_rest.repository;

import java.util.List;

public interface DaoCustomer<T> {
    T save(T obj);
    boolean delete(T obj);
    void deleteAll(List<T> entities);
    void saveAll(List<T> entities);
    List<T> findAll();
    boolean deleteById(long id);
    T getOne(long id);
}
