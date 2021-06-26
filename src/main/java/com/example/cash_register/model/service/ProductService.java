package com.example.cash_register.model.service;

import com.example.cash_register.controller.exceptions.ExistingProductNameException;
import com.example.cash_register.model.dao.DaoFactory;
import com.example.cash_register.model.dao.ProductDao;
import com.example.cash_register.model.entity.Product;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final DaoFactory repository = DaoFactory.getInstance();

    public final Product createProduct(Product product) {
        try (ProductDao productDao = repository.createProductDao()) {
            productDao.create(product);
        }catch (SQLException e){
            throw new ExistingProductNameException("Product with this name already exists");
        }
        return product;
    }


    public final List<Product> findAll(Integer page, String sortParam) {
        List<Product> res;
        try (ProductDao productDao = repository.createProductDao()) {
            res = productDao.findAll(page, sortParam);
        }
        return res;
    }

    public final Integer getPagesCount() {
        int res;
        try (ProductDao productDao = repository.createProductDao()) {
            res = productDao.getPagesCount();
        }
        return res;
    }

    public void deleteProduct(Long id) {
        try (ProductDao productDao = repository.createProductDao()) {
            productDao.delete(id);
        }
    }

    public Optional<Product> findById(Long id) {
        Optional<Product> product;
        try (ProductDao productDao = repository.createProductDao()) {
            product = productDao.findById(id);
        }
        return product;
    }
    public Optional<Product> findByName(String name) {
        Optional<Product> product;
        try (ProductDao productDao = repository.createProductDao()) {
            product = productDao.findByName(name);
        }
        return product;
    }

    public void updateAmount(boolean byWeight, String amount, Long id) {
        try (ProductDao productDao = repository.createProductDao()) {
            if (byWeight)
                productDao.updateWeight(Double.parseDouble(amount),id );
            else
                productDao.updateQuantity(Integer.parseInt(amount), id);
        }

    }

    public Optional<Product> findByNameOrId(String nameOrId) {
        Optional<Product> product = Optional.empty();
        try{
            product = findById(Long.parseLong(nameOrId));
        } catch (NumberFormatException e) {
            product = findByName(nameOrId);
        }
        return product;
    }
}
