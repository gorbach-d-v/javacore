package ru.yusdm.javacore.lesson12up13xml.autoservice.common.solutions.repo;

import java.util.List;

public interface BaseRepo<TYPE, ID> {

    void insert(TYPE entity);

    void update(TYPE entity);

    TYPE findById(ID id);

    void deleteById(ID id);

    void printAll();

    List<TYPE> findAll();
}
