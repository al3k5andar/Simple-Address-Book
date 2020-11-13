package com.am.simpleaddressbook.service;


import java.util.Set;

public interface BaseService <ID, T>
{
    public T save(T t);

    public Set<T> findAll();

    public T findById(ID id);

    public void delete(T t);

    public void deleteById(ID id);
}
