package com.example.cash_register.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseDao<E> extends AutoCloseable {

    void create(E entity) throws SQLException;

    Optional<E> findById(Long id);

    List<E> findAll(Integer page, String sortParam);

    List<E> findAll(Integer page);

    void update(E entity);

    void delete(Long id);

    void close();
}
