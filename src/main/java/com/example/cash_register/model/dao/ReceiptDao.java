package com.example.cash_register.model.dao;

import com.example.cash_register.model.entity.Product;
import com.example.cash_register.model.entity.Receipt;

import java.util.Map;
import java.util.Optional;

public interface ReceiptDao extends BaseDao<Receipt> {

    Optional<Receipt> createReceipt(Long userId);

    Optional<Receipt> getUnconfirmed(Long userId);

    void addProductInReceipt(Long receiptId, String nameOrId, Double amount) throws Exception;

    void confirm(Long receiptId);

    Optional<Map<Product, Double>> getXReportByProducts(Integer page);

    void updateReduceAmountInReceipt(long receiptId, long prodId, Double amount);

    void updateIncreaseAmountInReceipt(long receiptId, long prodId, Double amount);
}
