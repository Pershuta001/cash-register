package com.example.cash_register.model.dao.impl;

import com.example.cash_register.model.dao.ProductDao;
import com.example.cash_register.model.entity.Product;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;


public class JDBCProductDaoTest {


    private BasicDataSource ds;

    @Before
    public void setUp() {
        ds = new BasicDataSource();
        ds.setUrl("jdbc:postgresql://localhost:5432/test");
        ds.setUsername("postgres");
        ds.setPassword("oeuhr[erfoierj");
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }


    @Test
    public void shouldPassWhenCreateProduct() throws Exception {
        ProductDao dao = new JDBCProductDao(ds.getConnection());
        Product product = new Product.Builder().name("Tomato").byWeight(true).weight(10.).build();
        assertTrue(dao.create(product).getId() > 0);
        dao.delete(product.getId());
    }

    @Test(expected = SQLException.class)
    public void shouldPassWhenProductExists() throws Exception {
        ProductDao dao = new JDBCProductDao(ds.getConnection());
        Product product = new Product.Builder().name("Meat").byWeight(true).weight(10.).build();
        dao.create(product);
        dao.create(product);
    }

    @Test
    public void shouldPassWhenFoundProductByName() throws Exception {
        ProductDao dao = new JDBCProductDao(ds.getConnection());
        Product product = new Product.Builder().name("Pepper").byWeight(true).weight(10.).build();
        dao.create(product);
        Optional<Product> pepper = dao.findByNameOrId("Pepper");
        assertTrue(pepper.isPresent());
        dao.delete(pepper.get().getId());
    }

}