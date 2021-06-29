package com.example.cash_register.model.dao;

import com.example.cash_register.controller.view.ReportByCashiersView;
import com.example.cash_register.controller.view.ReportByProductsView;
import com.example.cash_register.model.entity.Receipt;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface ReceiptDao extends BaseDao<Receipt> {

    Optional<Receipt> createReceipt(Long userId);

    Optional<Receipt> getUnconfirmed(Long userId);

    void addProductInReceipt(Long receiptId, String nameOrId, Double amount) throws Exception;

    void confirm(Long receiptId);

    void updateReduceAmountInReceipt(long receiptId, long prodId, Double amount);

    void updateIncreaseAmountInReceipt(long receiptId, long prodId, Double amount);

    void deleteProductFromReceipt(Long receiptId, Long productId);

    List<ReportByProductsView> getXReportByProducts(Integer page);

    List<ReportByCashiersView> getXReportByCashiers(Integer page);

    void makeZReport();

    List<ReportByProductsView> getZReportByProducts(Date date, int page);

    List<ReportByCashiersView> getZReportByCashiers(Date date, int page);

    int getPagesCount(String param);

    int getZPagesCount(String param, Date date);
}
