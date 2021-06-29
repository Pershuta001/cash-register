package com.example.cash_register.model.service;

import com.example.cash_register.controller.view.ReportByCashiersView;
import com.example.cash_register.controller.view.ReportByProductsView;
import com.example.cash_register.model.dao.DaoFactory;
import com.example.cash_register.model.dao.ReceiptDao;
import com.example.cash_register.model.entity.Receipt;

import java.sql.Date;
import java.util.List;
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

    public List<ReportByCashiersView> getXReportByCashiers(Integer page) {
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            return receiptDao.getXReportByCashiers(page);
        }

    }

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

    public Optional<Receipt> findById(Long id) {
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            return receiptDao.findById(id);
        }

    }

    public void deleteProductFromReceipt(Long receiptId, Long productId) {
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            receiptDao.deleteProductFromReceipt(receiptId, productId);
        }
    }

    public List<ReportByProductsView> getXReportByProducts(Integer page) {
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            return receiptDao.getXReportByProducts(page);
        }
    }

    public void makeZReport(){
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            receiptDao.makeZReport();
        }
    }

    public List<ReportByProductsView> getZReportByProducts(Date date, Integer page) {
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            return receiptDao.getZReportByProducts(date, page);
        }
    }

    public List<ReportByCashiersView> getZReportByCashiers(Date date, Integer page) {
        try (ReceiptDao receiptDao = repository.createReceiptDao()) {
            return receiptDao.getZReportByCashiers(date, page);
        }
    }

    public int getPagesCount(String param) {
        try(ReceiptDao receiptDao = repository.createReceiptDao()){
            return receiptDao.getPagesCount(param);
        }
    }

    public int getZPagesCount( String param, Date date) {
        try(ReceiptDao receiptDao = repository.createReceiptDao()){
            return receiptDao.getZPagesCount(param, date);
        }
    }
}
