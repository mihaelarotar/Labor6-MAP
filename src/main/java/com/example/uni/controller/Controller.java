package com.example.uni.controller;

import com.example.uni.repository.ICrudRepository;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public abstract class Controller<E> {
    protected final ICrudRepository<E> repository;

    protected Controller(ICrudRepository<E> repository) {
        this.repository = repository;
    }

    /**
     * adds given entity
     *
     * @param entity entity must be not null
     */
    public void add(E entity) {
        repository.save(entity);
    }

    /**
     * removes the entity with the specified id
     * @param entity entity must not be null
     */
    public void delete(E entity) {
        repository.delete(entity);
    }

    /**
     * updates given entity
     * @param entity entity must not be null
     */
    public void update(E entity) {
        repository.update(entity);
    }


    /**
     * @return all entities
     */
    public List<E> getAll() throws SQLException {
        return repository.getAll();
    }

    /**
     * general filter function for entities with certain property
     * @param function to apply to each entity to determine if it should be included
     * @return a new list containing only the entities with the given property
     */
    public List<E> filter(Predicate<E> function) throws SQLException {
        return repository.getAll()
                .stream()
                .filter(function)
                .toList();
    }

    /**
     * general sort function
     * @param comparator used to compare list elements
     */
    public void sort(Comparator<E> comparator) throws SQLException {
        repository.getAll().sort(comparator);
    }

}
