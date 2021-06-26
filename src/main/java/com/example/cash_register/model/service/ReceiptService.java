package com.example.cash_register.model.service;

import com.example.cash_register.controller.exceptions.NoSuchProductException;
import com.example.cash_register.controller.view.XReportByCashiersView;
import com.example.cash_register.model.dao.DaoFactory;
import com.example.cash_register.model.dao.ReceiptDao;
import com.example.cash_register.model.entity.Product;
import com.example.cash_register.model.entity.Receipt;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReceiptService {

    private final DaoFactory repository = DaoFactory.getInstance();

    public Optional<Receipt> createReceipt(Long userId) {
        Optional<Receipt> receipt;
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            receipt = receiptDao.createReceipt(userId);
        }
        return receipt;
    }

    public Optional<Receipt> getUnconfirmedCheck(Long userId) {
        Optional<Receipt> receipt;
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            receipt = receiptDao.getUnconfirmed(userId);
        }
        return receipt;
    }

    public void addProductInReceipt(Long receiptId, String nameOrId, Double amount) throws Exception {
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            receiptDao.addProductInReceipt(receiptId, nameOrId, amount);
        }
    }

    public Optional<Map<Product, Double>> getXReport(Integer page, String sort) {
        Optional<Map<Product, Double>> res;
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            res = receiptDao.getXReportByProducts(page);
        }
        return res;
    }

//    public Optional<List<XReportByCashiersView>> getXReportByCashier(Integer page, String sort) {
//        Optional<Map<Product, Double>> res;
//        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
//            res = receiptDao.getXReportByProducts(page);
//        }
//        return res;
//    }
//    public Optional<List<XReportByCashiersView>> getXReportByProduct(Integer page, String sort) {
//        Optional<Map<Product, Double>> res;
//        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
//            res = receiptDao.getXReportByProducts(page);
//        }
//        return res;
//    }

    public void confirmReceipt(Long receiptId) {
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            receiptDao.confirm(receiptId);
        }
    }

    public List<Receipt> findAll(Integer page) {
        List<Receipt> res;
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            res = receiptDao.findAll(page);
        }
        return res;
    }

    public Optional<Receipt> getReceiptById(Long receiptId) {
        Optional<Receipt> receipt;
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            receipt = receiptDao.findById(receiptId);
        }
        return receipt;

    }

    public void updateReduceAmountInReceipt(long receiptId, long prodId, double amount) {
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
             receiptDao.updateReduceAmountInReceipt(receiptId, prodId, amount);
        }
    }

    public void updateIncreaseAmountInReceipt(long receiptId, long prodId, double amount) {
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            receiptDao.updateIncreaseAmountInReceipt(receiptId, prodId, amount);
        }
    }

    public void deleteReceipt(Long id) {
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            receiptDao.delete(id);
        }
    }
}
