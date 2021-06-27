package com.example.cash_register.model.dao.impl;

import com.example.cash_register.controller.exceptions.NoSuchProductException;
import com.example.cash_register.controller.exceptions.NoSuchReceiptException;
import com.example.cash_register.controller.exceptions.NotEnoughProductAmountException;
import com.example.cash_register.controller.view.XReportByCashiersView;
import com.example.cash_register.model.dao.ProductDao;
import com.example.cash_register.model.dao.ReceiptDao;
import com.example.cash_register.model.dao.mapper.ReceiptMapper;
import com.example.cash_register.model.entity.Product;
import com.example.cash_register.model.entity.Receipt;
import com.example.cash_register.utils.Constants;


import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JDBCReceiptDao implements ReceiptDao {

    private final Connection connection;

    private static final String DELETE_RECEIPT_BY_ID = "DELETE FROM receipts WHERE id = ?";

    private static final String GET_PRODUCT_AMOUNT_IN_RECEIPT =
            "SELECT coalesce(weight, amount) as amount " +
                    "from receipt_product " +
                    "WHERE  product_id =  ? and receipt_id = ?";

    private static final String DELETE_PRODUCT_IN_RECEIPT =
            "DELETE " +
                    "from receipt_product " +
                    "WHERE  product_id =  ? and receipt_id = ?";

    private final static String CREATE_RECEIPT =
            "INSERT INTO receipts(cashier_id, date) VALUES (?,?)";

    private final static String ADD_PRODUCT_IN_RECEIPT =
            "INSERT INTO receipt_product(receipt_id, product_id, amount, weight) VALUES (?,?,?,?);";

    private final static String UPDATE_PRODUCT_AMOUNT =
            "UPDATE products " +
                    "SET weight = weight - ?, quantity = quantity - ? " +
                    "WHERE id = ?";

    private final static String UPDATE_PRODUCT_AMOUNT_IN_RECEIPT =
            "UPDATE receipt_product " +
                    "SET amount = amount + ?, weight = weight + ? " +
                    "WHERE receipt_id = ? AND product_id = ?";

    private final static String UPDATE_DATE_IN_RECEIPT =
            "UPDATE receipts " +
                    "SET date = ? " +
                    "WHERE id = ?";

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
            "SELECT product_id, p.name, p.price,p.byWeight, r.id,r.cashier_id, r.date, rp.amount, rp.weight " +
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

    private final static  String GET_X_REPORT_BY_CASHIERS =
            "SELECT u.id, u.name, u.surname, count(distinct (r.id)) as completed_receipts, sum(coalesce(rp.weight, rp.amount)*p.price) as cost \n" +
                    "from users u\n" +
                    "         INNER JOIN receipts r on u.id = r.cashier_id\n" +
                    "         INNER JOIN receipt_product rp on r.id = rp.receipt_id\n" +
                    "         INNER JOIN products p on p.id = rp.product_id\n" +
                    "group by u.id, u.name, u.surname\n" +
                    "limit ? offset ?;";

    private final static String GET_ALL_RECEIPTS_WITH_PRODUCTS =
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

    private final static String GET_ALL_RECEIPTS =
            "SELECT *\n" +
                    "from receipts\n" +
                    "WHERE date IS NOT NULL\n" +
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
    public void addProductInReceipt(Long receiptId, String nameOrId, Double amount) throws Exception {
        try {
            connection.setAutoCommit(false);
            ProductDao productDao = new JDBCProductDao(connection);
            Optional<Product> product = productDao.findByNameOrId(nameOrId);
            if (!product.isPresent()) {
                throw new NoSuchProductException("name ex");
            }
            Product prod = product.get();
            double available = prod.isByWeight() ? prod.getAvailableWeight() : prod.getAvailableQuantity();
            if (available < amount) {
                throw new NotEnoughProductAmountException("amount ex");
            }
            Optional<Receipt> receiptOpt = findById(receiptId);
            if (!receiptOpt.isPresent()) {
                throw new NoSuchReceiptException("No such receipt");
            }
            Receipt receipt = receiptOpt.get();

            if (checkInReceipt(prod, receipt)) {
                updateAmount(receiptId, prod.getId(), amount);
            } else {
                addNewProductInReceipt(receiptId, prod.getId(), prod.isByWeight(), amount);
            }
            updateProductAmount(prod.getId(), amount);
        } catch (Exception e) {
            DBUtils.rollback(connection);
            throw new Exception(e.getMessage());
        } finally {
            DBUtils.commit(connection);
        }
    }

    private void updateProductAmount(Long id, Double amount) {
        PreparedStatement statement = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_PRODUCT_AMOUNT);
            statement.setDouble(1, amount);
            statement.setInt(2, amount.intValue());
            statement.setLong(3, id);

            statement.executeUpdate();
        } catch (SQLException throwables) {
            DBUtils.rollback(connection);
        } finally {
            DBUtils.commit(connection);
        }


    }

    private void addNewProductInReceipt(Long receiptId, Long productId, boolean isByWeight, Double amount) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(ADD_PRODUCT_IN_RECEIPT);
        statement.setLong(1, receiptId);
        statement.setLong(2, productId);
        if (isByWeight) {
            statement.setNull(3, Types.NULL);
            statement.setDouble(4, amount);
        } else {
            statement.setNull(4, Types.NULL);
            statement.setInt(3, amount.intValue());
        }
        statement.executeUpdate();
    }

    private boolean checkInReceipt(Product prod, Receipt receipt) {
        return receipt.getProductsInReceipt().entrySet()
                .stream()
                .anyMatch(p -> p.getKey().getId().equals(prod.getId()));

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

    private void updateAmount(Long receiptId, Long id, Double amount) {
        PreparedStatement statement;
        try {
            statement = connection.prepareStatement(UPDATE_PRODUCT_AMOUNT_IN_RECEIPT);
            statement.setDouble(2, amount);
            statement.setInt(1, amount.intValue());
            statement.setLong(3, receiptId);
            statement.setLong(4, id);
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
            res = new ReceiptMapper().extractFromResultSet(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Receipt> findAll(Integer page, String sortParam) {
        return null;
    }

    public List<XReportByCashiersView> getXReportByCashiers(Integer page) {
        PreparedStatement stmt;
        ResultSet rs;
        List<XReportByCashiersView> res = null;
        try {
            stmt = connection.prepareStatement(GET_X_REPORT_BY_CASHIERS);
            stmt.setInt(1, Constants.PAGE_SIZE);
            stmt.setInt(2, Constants.PAGE_SIZE * (page - 1));
            rs = stmt.executeQuery();
            ReceiptMapper mapper = new ReceiptMapper();
            res = mapper.extractListReportFromResultSet(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    @Override
    public void updateReduceAmountInReceipt(long receiptId, long prodId, Double amount) {
        PreparedStatement statement;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(UPDATE_PRODUCT_AMOUNT_IN_RECEIPT);
            statement.setInt(1, -amount.intValue());
            statement.setDouble(2, -amount);
            statement.setLong(3, receiptId);
            statement.setLong(4, prodId);
            statement.executeUpdate();

            statement = connection.prepareStatement(UPDATE_PRODUCT_AMOUNT);
            statement.setDouble(1, -amount);
            statement.setInt(2, -amount.intValue());
            statement.setLong(3, prodId);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            DBUtils.rollback(connection);
        } finally {
            DBUtils.commit(connection);
        }
    }

    @Override
    public void updateIncreaseAmountInReceipt(long receiptId, long prodId, Double amount) {
        try {
            connection.setAutoCommit(false);
            ProductDao productDao = new JDBCProductDao(connection);
            Optional<Product> productOptional = productDao.findById(prodId);
            if (!productOptional.isPresent()) {
                throw new NoSuchProductException("No such product");
            }
            Product product = productOptional.get();
            double available = product.isByWeight() ? product.getAvailableWeight() : product.getAvailableQuantity();
            if (available < amount) {
                throw new NotEnoughProductAmountException("Not enough product in store");
            }
            updateAmount(receiptId, prodId, amount);
            updateProductAmount(prodId, amount);
        } catch (SQLException throwables) {
            DBUtils.rollback(connection);
        } finally {
            DBUtils.commit(connection);
        }
    }

    @Override
    public void deleteProductFromReceipt(Long receiptId, Long productId) {

        PreparedStatement statement;
        ResultSet rs;

        try {
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(GET_PRODUCT_AMOUNT_IN_RECEIPT);
            statement.setLong(1, productId);
            statement.setLong(2, receiptId);

            rs = statement.executeQuery();
            double amount = 0;

            if (rs.next()) {
                amount = rs.getDouble("amount");
            }
            updateProductAmount(productId, -amount);
            statement = connection.prepareStatement(DELETE_PRODUCT_IN_RECEIPT);
            statement.setLong(1, productId);
            statement.setLong(2, receiptId);

            statement.executeUpdate();

        } catch (SQLException throwables) {
            DBUtils.rollback(connection);
        } finally {
            DBUtils.commit(connection);
        }

    }

    @Override
    public List<Receipt> findAll(Integer page) {
        PreparedStatement stmt;
        ResultSet rs;
        ReceiptMapper mapper = new ReceiptMapper();
        List<Receipt> res = null;
        try {
            stmt = connection.prepareStatement(GET_ALL_RECEIPTS);
            stmt.setInt(1, Constants.PAGE_SIZE);
            stmt.setInt(2, Constants.PAGE_SIZE * (page - 1));
            rs = stmt.executeQuery();
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
        PreparedStatement statement;
        try {
            connection.setAutoCommit(false);
            Optional<Receipt> receiptOptional = findById(id);
            if (!receiptOptional.isPresent()) {
                throw new NoSuchReceiptException("No such receipt Exception");
            }
            Receipt receipt = receiptOptional.get();
            receipt.getProductsInReceipt().forEach((key, value) -> updateProductAmount(key.getId(), -value));
            statement = connection.prepareStatement(DELETE_RECEIPT_BY_ID);
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            DBUtils.rollback(connection);
        } finally {
            DBUtils.commit(connection);
        }
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
