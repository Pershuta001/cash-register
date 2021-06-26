package com.example.cash_register.model.dao.impl;

import com.example.cash_register.model.dao.ProductDao;
import com.example.cash_register.model.dao.mapper.ProductMapper;
import com.example.cash_register.model.entity.Product;
import com.example.cash_register.utils.Constants;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static java.sql.Types.*;

public class JDBCProductDao implements ProductDao {


    private final Connection connection;

    private static final String FIND_ALL_BY_ID =
            "SELECT * FROM products Order By id limit ? OFFSET ?";


    private static final String FIND_ALL_BY_ID_REVERSE =
            "SELECT * FROM products Order By id DESC limit ? OFFSET ?";

    private static final String COUNT_ALL_PRODUCTS =
            "SELECT COUNT(*) FROM products";

    private final static String CREATE_PRODUCT =
            "INSERT INTO products(name, price, quantity, byweight,weight) VALUES (?,?,?,?,?)";

    private final static String FIND_BY_ID =
            "SELECT * FROM products WHERE id = ?";

    private final static String FIND_BY_NAME_OR_ID =
            "SELECT * FROM products WHERE name = ? OR id = ?";

    private final static String FIND_ALL_SORT_BY_PRICE_FROM_CHEEP =
            "SELECT * " +
                    "FROM products " +
                    "ORDER BY price limit ? OFFSET ?";

    private final static String FIND_ALL_SORT_BY_PRICE_FROM_EXPENSIVE =
            "SELECT * " +
                    "FROM products " +
                    "ORDER BY price DESC limit ? OFFSET ?";

    private final static String FIND_ALL_SORT_BY_NAME =
            "SELECT * " +
                    "FROM products " +
                    "ORDER BY name limit ? OFFSET ?";

    private final static String FIND_ALL_SORT_BY_NAME_DESC =
            "SELECT * " +
                    "FROM products " +
                    "ORDER BY name DESC limit ? OFFSET ?";

    private final static String FIND_ALL_SORT_BY_AMOUNT =
            "SELECT * " +
                    "FROM products " +
                    "ORDER BY coalesce(quantity, weight) " +
                    "limit ? OFFSET ?";

    private final static String FIND_ALL_SORT_BY_AMOUNT_REVERSE =
            "SELECT * " +
                    "FROM products " +
                    "ORDER BY coalesce(quantity, weight) DESC " +
                    "limit ? OFFSET ?";

    private final static String UPDATE_PRODUCT_QUANTITY =
            "        UPDATE products " +
                    "Set quantity = ? " +
                    "WHERE id = ? ";

    private final static String UPDATE_PRODUCT_WEIGHT =
            "        Update products " +
                    "Set weight = ? " +
                    "WHERE id = ? ";

    private final static String DELETE_PRODUCT =
            "DELETE from products WHERE id = ?";

    public JDBCProductDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Product entity) throws SQLException {
        PreparedStatement stmt;
        ResultSet resultSet;
        try {
            connection.setAutoCommit(false);
            stmt = connection.prepareStatement(CREATE_PRODUCT, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getName());
            stmt.setDouble(2, entity.getPrice());
            stmt.setBoolean(4, entity.isByWeight());

            if (entity.isByWeight()) {
                stmt.setNull(3, NULL);
                stmt.setDouble(5, entity.getAvailableWeight());
            } else {
                stmt.setNull(5, NULL);
                stmt.setInt(3, entity.getAvailableQuantity());
            }
            stmt.executeUpdate();
            connection.commit();
            resultSet = stmt.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong(1));
            }
        } catch (SQLException ex) {
            DBUtils.rollback(connection);
            throw new SQLException("Creating product failed");
        } finally {
            close();
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        PreparedStatement stmt;
        ResultSet rs;
        ProductMapper mapper = new ProductMapper();
        Optional<Product> res = Optional.empty();
        try {
            stmt = connection.prepareStatement(FIND_BY_ID);
            stmt.setLong(1, id);

            rs = stmt.executeQuery();
            if (rs.next()) {
                res = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Product> findAll(Integer page, String sortParam) {
        PreparedStatement stmt;
        ResultSet rs;
        ProductMapper mapper = new ProductMapper();
        List<Product> res = null;
        try {
            switch (sortParam) {
                case "fromCheep":
                    stmt = connection.prepareStatement(FIND_ALL_SORT_BY_PRICE_FROM_CHEEP);
                    break;
                case "fromExpensive":
                    stmt = connection.prepareStatement(FIND_ALL_SORT_BY_PRICE_FROM_EXPENSIVE);
                    break;
                case "byAlphabet":
                    stmt = connection.prepareStatement(FIND_ALL_SORT_BY_NAME);
                    break;
                case "byAlphabetReverse":
                    stmt = connection.prepareStatement(FIND_ALL_SORT_BY_NAME_DESC);
                    break;
                case "byAmount":
                    stmt = connection.prepareStatement(FIND_ALL_SORT_BY_AMOUNT);
                    break;
                case "byAmountReverse":
                    stmt = connection.prepareStatement(FIND_ALL_SORT_BY_AMOUNT_REVERSE);
                    break;
                case "byIdReverse":
                    stmt = connection.prepareStatement(FIND_ALL_BY_ID_REVERSE);
                    break;
                default:
                    stmt = connection.prepareStatement(FIND_ALL_BY_ID);
            }
            stmt.setInt(1, Constants.PAGE_SIZE);
            stmt.setInt(2, (page - 1) * Constants.PAGE_SIZE);
            rs = stmt.executeQuery();
            res = mapper.extractListFromResultSet(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }


    public List<Product> findAll(Integer page) {
        PreparedStatement stmt;
        ResultSet rs;
        ProductMapper mapper = new ProductMapper();
        List<Product> res = null;
        try {
            stmt = connection.prepareStatement(FIND_ALL_BY_ID);
            stmt.setInt(1, (page - 1) * 10);
            rs = stmt.executeQuery();
            res = mapper.extractListFromResultSet(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public void update(Product entity) {

    }

    @Override
    public void delete(Long id) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(DELETE_PRODUCT);
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public int getPagesCount() {
        Statement stmt;
        ResultSet rs;
        int res = 0;
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(COUNT_ALL_PRODUCTS);
            if (rs.next())
                res = rs.getInt(1);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        res = res / Constants.PAGE_SIZE + (res % Constants.PAGE_SIZE == 0 ? 0 : 1);
        return res;
    }

    @Override
    public void updateWeight(double weight, Long id) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(UPDATE_PRODUCT_WEIGHT);
            stmt.setDouble(1, weight);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void updateQuantity(int quantity, Long id) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(UPDATE_PRODUCT_QUANTITY);
            stmt.setDouble(1, quantity);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<Product> findByNameOrId(String name) {
        PreparedStatement stmt;
        ResultSet rs;
        ProductMapper mapper = new ProductMapper();
        Optional<Product> res = Optional.empty();
        try {
            stmt = connection.prepareStatement(FIND_BY_NAME_OR_ID);
            stmt.setString(1, name);
            try {
                long id = Long.parseLong(name);
                stmt.setLong(2, id);
            } catch (NumberFormatException e) {
                stmt.setLong(2, 0);
            }
            rs = stmt.executeQuery();
            if (rs.next()) {
                res = Optional.of(mapper.extractFromResultSet(rs));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
