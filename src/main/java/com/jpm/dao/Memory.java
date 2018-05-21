package com.jpm.dao;

import com.jpm.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  This class is the way to keep/persist the sales and the messages on the memory
 *  SaleMemory keeps only sale messages by productType
 *  AdjustmentMemory keeps both sale and adjustment messages by productType and message order
 */
public class Memory implements Persistence {

    private Map<ProductType, List<Sale>> saleMemory;
    private Map<ProductType, Map<Integer, Message>> adjustmentMemory;

    public Memory(){
        saleMemory = new HashMap<>();
        adjustmentMemory = new HashMap<>();
    }

    @Override
    public void saveSale(Sale sale){
        List<Sale> saleList = saleMemory.get(sale.getProductType());
        if(saleList == null) {
            saleList = new ArrayList<>();
        }
        saleList.add(sale);
        saleMemory.put(sale.getProductType(), saleList);
    }

    @Override
    public void saveAdjustment(Message message){
        Map<Integer, Message> adjustmentList = adjustmentMemory.get(message.getProductType());
        if(adjustmentList == null) {
            adjustmentList = new HashMap<>();
        }
        adjustmentList.put(message.getId(), message);
        adjustmentMemory.put(message.getProductType(), adjustmentList);
    }

    @Override
    public void updateSalesByProductType(ProductType productType, List<Sale> sales){
        saleMemory.put(productType, sales);
    }

    @Override
    public Map<ProductType, List<Sale>> getSales(){
        return saleMemory;
    }

    @Override
    public Map<ProductType, Map<Integer, Message>> getAdjustments(){
        return adjustmentMemory;
    }
}
