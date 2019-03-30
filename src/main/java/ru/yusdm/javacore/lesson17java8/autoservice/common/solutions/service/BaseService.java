package ru.yusdm.javacore.lesson17java8.autoservice.common.solutions.service;

import ru.yusdm.javacore.lesson17java8.autoservice.common.business.exception.AutoServiceUncheckedException;

import java.util.Collection;
import java.util.List;

public interface BaseService<TYPE, ID> {

    TYPE insert(TYPE entity);

    void insert(Collection<TYPE> items);

    void update(TYPE entity);

    TYPE findById(ID id);

    void deleteById(ID id) throws AutoServiceUncheckedException;

    void delete(TYPE entity);

    void printAll();

    List<TYPE> findAll();

    int countAll();

}

