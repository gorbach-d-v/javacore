package ru.yusdm.javacore.lesson17up18up19java8.autoservice.common.solutions.repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BaseRepo<TYPE, ID> {

    TYPE insert(TYPE entity);

    void insert(Collection<TYPE> items);

    void update(TYPE entity);

    Optional<TYPE> findById(ID id);

    void deleteById(ID id);

    void printAll();

    List<TYPE> findAll();

    int countAll();
}
