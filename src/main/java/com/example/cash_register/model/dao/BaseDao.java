package com.example.cash_register.model.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BaseDao<E> extends AutoCloseable {

    E create(E entity) throws SQLException;

    Optional<E> findById(Long id);

    List<E> findAll(Integer page, String sortParam) throws SQLException;

    List<E> findAll(Integer page) throws SQLException;

    void delete(Long id) throws SQLException;

    void close();
}
