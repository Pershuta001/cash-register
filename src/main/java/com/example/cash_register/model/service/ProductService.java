package com.example.cash_register.model.service;

import com.example.cash_register.controller.exceptions.ExistingProductNameException;
import com.example.cash_register.model.dao.DaoFactory;
import com.example.cash_register.model.dao.ProductDao;
import com.example.cash_register.model.entity.Product;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final DaoFactory repository = DaoFactory.getInstance();
    private final Logger log = Logger.getLogger(ProductService.class);

    public final Product createProduct(Product product) {
        try (ProductDao productDao = repository.createProductDao()) {
            productDao.create(product);
        } catch (SQLException e) {
            log.error("Product with this name '" + product.getName() + "' already exists");
            throw new ExistingProductNameException("Product with this name already exists");

        }
        return product;
    }


    public final List<Product> findAll(Integer page, String sortParam) {
        List<Product> res;
        try (ProductDao productDao = repository.createProductDao()) {
            res = productDao.findAll(page, sortParam);
            log.debug("found products by page");
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage());
        }
        return res;
    }

    public final Integer getPagesCount() {
        int res;
        try (ProductDao productDao = repository.createProductDao()) {
            res = productDao.getPagesCount();
            log.debug("count number of pages");
        } catch (SQLException throwables) {
            throw new RuntimeException(throwables.getMessage());
        }
        return res;
    }

    public void deleteProduct(Long id) {
        try (ProductDao productDao = repository.createProductDao()) {
            productDao.delete(id);
            log.debug("Product with id '" + id + "' was deleted");
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables.getMessage());
        }
    }

    public Optional<Product> findById(Long id) {
        Optional<Product> product;
        try (ProductDao productDao = repository.createProductDao()) {
            product = productDao.findById(id);
            log.debug("Attempt to find product with id '" + id + "'");
        }
        return product;
    }

    public void updateAmount(boolean byWeight, String amount, Long id) {
        try (ProductDao productDao = repository.createProductDao()) {
            log.debug("Attempt to update product amount by id '" + id + "'");
            if (byWeight) {
                productDao.updateWeight(Double.parseDouble(amount), id);
            } else {
                productDao.updateQuantity(Integer.parseInt(amount), id);
            }
        } catch (SQLException throwables) {
            throw new IllegalArgumentException(throwables.getMessage());
        }
    }
}
