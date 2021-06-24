package com.example.cash_register.model.dao.impl;

import com.example.cash_register.model.dao.ReceiptDao;
import com.example.cash_register.model.dao.mapper.ReceiptMapper;
import com.example.cash_register.model.entity.Product;
import com.example.cash_register.model.entity.Receipt;
import com.example.cash_register.model.service.ProductService;
import com.example.cash_register.utils.Constants;


import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JDBCReceiptDao implements ReceiptDao {

    private final Connection connection;

    private final static String CREATE_RECEIPT =
            "INSERT INTO receipts(cashier_id, date) VALUES (?,?)";

    private final static String ADD_PRODUCT_IN_RECEIPT =
            "INSERT INTO receipt_product(receipt_id, product_id, amount, weight) VALUES (?,?,?,?)";

    private final static String UPDATE_PRODUCT_AMOUNT_IN_RECEIPT =
            "UPDATE receipt_product " +
                    "SET amount = ? " +
                    "WHERE receipt_id = ? AND product_id = ?";

    private final static String UPDATE_DATE_IN_RECEIPT =
            "UPDATE receipts " +
                    "SET date = ? " +
                    "WHERE id = ?";

    private final static String UPDATE_PRODUCT_WEIGHT_IN_RECEIPT =
            "UPDATE receipt_product " +
                    "SET weight = ? " +
                    "WHERE receipt_id = ? AND product_id = ?";

    private final static String GET_RECEIPT_WITH_NULL_DATE =
            "SELECT product_id, p.name, p.price,p.byWeight, r.id, rp.amount, rp.weight " +
                    "from receipts r " +
                    "         LEFT OUTER JOIN receipt_product rp on r.id = rp.receipt_id " +
                    "         LEFT OUTER JOIN products p on p.id = rp.product_id " +
                    "WHERE r.id IN ( " +
                    "    SELECT id " +
                    "    FROM receipts " +
                    "    WHERE cashier_id = ? AND date IS NULL " +
                    ")";

    private final static String GET_RECEIPT_BY_ID =
            "SELECT product_id, p.name, p.price,p.byWeight, r.id, rp.amount, rp.weight " +
                    "from receipts r " +
                    "         LEFT OUTER JOIN receipt_product rp on r.id = rp.receipt_id " +
                    "         LEFT OUTER JOIN products p on p.id = rp.product_id " +
                    "WHERE r.id = ?";

    private final static String GET_X_REPORT_BY_PRODUCTS =
            "SELECT product_id, p.name, p.price, p.byweight, sum(rp.amount) as quantity, sum(rp.weight) as weight " +
                    "from receipt_product rp " +
                    "         INNER JOIN products p on p.id = rp.product_id " +
                    "group by product_id, p.name, p.price, p.byweight" +
                    " limit ? OFFSET ?";

    private final static String GET_ALL_RECEIPTS =
            "SELECT product_id, p.name, p.price,p.byWeight, r.id, rp.amount, rp.weight " +
                    "from receipts r " +
                    "         LEFT OUTER JOIN receipt_product rp on r.id = rp.receipt_id " +
                    "         LEFT OUTER JOIN products p on p.id = rp.product_id " +
                    "WHERE r.id IN ( " +
                    "    SELECT id " +
                    "    FROM receipts " +
                    "    WHERE date IS NOT NULL " +
                    ") " +
                    "limit ? OFFSET ?";


    public JDBCReceiptDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Receipt entity) {

    }

    @Override
    public Optional<Receipt> createReceipt(Long userId) {
        PreparedStatement stmt;
        ResultSet rs;
        Receipt receipt = null;
        try {
            stmt = connection.prepareStatement(CREATE_RECEIPT, Statement.RETURN_GENERATED_KEYS);
            stmt.setLong(1, userId);
            stmt.setNull(2, java.sql.Types.NULL);
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                receipt = new Receipt();
                receipt.setId(rs.getLong(1));
                receipt.setCashierId(userId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.ofNullable(receipt);

    }

    @Override
    public Optional<Receipt> getUnconfirmed(Long userId) {
        PreparedStatement stmt;
        ResultSet rs;
        Optional<Receipt> res = Optional.empty();
        try {
            stmt = connection.prepareStatement(GET_RECEIPT_WITH_NULL_DATE);
            stmt.setLong(1, userId);
            rs = stmt.executeQuery();
            ReceiptMapper mapper = new ReceiptMapper();
            res = mapper.extractFromResultSet(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public void addProductInReceipt(Long receiptId, String nameOrId, Double amount) {
        ProductService productService = new ProductService();
        Optional<Product> product = productService.findByNameOrId(nameOrId);
        if (!product.isPresent()) {
            return;
        }
        Receipt receipt = findById(receiptId).get();
        Optional<Map.Entry<Product, Double>> prodInReceipt =
                receipt.getProductsInReceipt().entrySet().stream().filter
                        (p -> p.getKey().getId().equals(product.get().getId())).findFirst();

        if (prodInReceipt.isPresent()) {
            updateAmount(receiptId, product.get().getId(), product.get().isByWeight(), prodInReceipt.get().getValue() + amount);
            return;
        }
        try {

            PreparedStatement statement = connection.prepareStatement(ADD_PRODUCT_IN_RECEIPT);
            statement.setLong(1, receiptId);
            statement.setLong(2, product.get().getId());
            if (product.get().isByWeight()) {
                statement.setNull(3, Types.NULL);
                statement.setDouble(4, amount);
            } else {
                statement.setNull(4, Types.NULL);
                statement.setInt(3, amount.intValue());
            }
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void confirm(Long receiptId) {
        PreparedStatement stmt;
        try {
            stmt = connection.prepareStatement(UPDATE_DATE_IN_RECEIPT);
            stmt.setDate(1, new Date(new java.util.Date().getTime()));
            stmt.setLong(2, receiptId);
            stmt.executeUpdate();
            ReceiptMapper mapper = new ReceiptMapper();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void updateAmount(Long receiptId, Long id, Boolean byWeight, Double amount) {
        PreparedStatement statement;
        try {
            if (byWeight) {
                statement = connection.prepareStatement(UPDATE_PRODUCT_WEIGHT_IN_RECEIPT);
                statement.setDouble(1, amount);
            } else {
                statement = connection.prepareStatement(UPDATE_PRODUCT_AMOUNT_IN_RECEIPT);
                statement.setInt(1, amount.intValue());
            }

            statement.setLong(2, receiptId);
            statement.setLong(3, id);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @Override
    public Optional<Receipt> findById(Long id) {
        PreparedStatement stmt;
        ResultSet rs;
        Optional<Receipt> res = Optional.empty();
        try {
            stmt = connection.prepareStatement(GET_RECEIPT_BY_ID);
            stmt.setLong(1, id);
            rs = stmt.executeQuery();
            ReceiptMapper mapper = new ReceiptMapper();
            res = mapper.extractFromResultSet(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Receipt> findAll(Integer page, String sortParam) {
        return null;
    }

    public Optional<Map<Product, Double>> getXReportByProducts(Integer page) {
        PreparedStatement stmt;
        ResultSet rs;
        Optional<Map<Product, Double>> res = Optional.empty();
        try {
            stmt = connection.prepareStatement(GET_X_REPORT_BY_PRODUCTS);
            stmt.setInt(1, Constants.PAGE_SIZE);
            stmt.setInt(2, Constants.PAGE_SIZE * (page - 1));
            rs = stmt.executeQuery();
            ReceiptMapper mapper = new ReceiptMapper();
            res = mapper.extractXProductReport(rs);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }


    @Override
    public List<Receipt> findAll(Integer page) {
        PreparedStatement stmt;
        ResultSet rs;
        List<Receipt> res = null;
        try {
            stmt = connection.prepareStatement(GET_ALL_RECEIPTS);
            stmt.setInt(1, Constants.PAGE_SIZE);
            stmt.setInt(2, Constants.PAGE_SIZE * (page - 1));
            rs = stmt.executeQuery();
            ReceiptMapper mapper = new ReceiptMapper();
            res = mapper.extractListFromResultSet(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public void update(Receipt entity) {

    }

    @Override
    public void delete(Long id) {

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
