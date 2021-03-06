package ru.yusdm.javacore.lesson15up16concurrency.autoservice.common.solutions.repo;

import java.util.Collection;
import java.util.List;

public interface BaseRepo<TYPE, ID> {

    void insert(TYPE entity);

    void insert(Collection<TYPE> items);

    void update(TYPE entity);

    TYPE findById(ID id);

    void deleteById(ID id);

    void printAll();

    List<TYPE> findAll();

    int countAll();
}
