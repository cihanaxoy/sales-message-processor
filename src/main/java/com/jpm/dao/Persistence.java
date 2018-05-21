package com.jpm.dao;

import com.jpm.model.Message;
import com.jpm.model.ProductType;
import com.jpm.model.Sale;

import java.util.List;
import java.util.Map;

/**
 *  This interface provides the methods for data persistence
 */
public interface Persistence {

    void saveSale(Sale sale);
    void saveAdjustment(Message message);
    void updateSalesByProductType(ProductType productType, List<Sale> sales);
    Map<ProductType, List<Sale>> getSales();
    Map<ProductType, Map<Integer, Message>> getAdjustments();
}
