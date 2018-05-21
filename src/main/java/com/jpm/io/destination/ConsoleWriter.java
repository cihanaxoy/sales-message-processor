package com.jpm.io.destination;

import com.jpm.model.Message;
import com.jpm.model.ProductType;
import com.jpm.model.Sale;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *  This class is to listen to the service and print sales and adjustment logs to the console
 */
public class ConsoleWriter implements ReportWriterListener {

    public ConsoleWriter(){
    }

    @Override
    public void writeSalesLogs(Map<ProductType, List<Sale>> saleLogs){

        for (Map.Entry<ProductType, List<Sale>> entry : saleLogs.entrySet()) {
            int totalAmount = 0, totalPrice = 0;
            for(Sale sale : entry.getValue()){
                totalAmount += sale.getSaleAmount();
                totalPrice += sale.getSalePrice();
            }
            String sale = totalAmount == 1 ? "sale" : "sales";
            System.out.println(totalAmount + " " + sale + " at the price " + totalPrice + " for the product " + entry.getKey());
        }
    }

    @Override
    public void writeAdjustmentLogs(Map<ProductType, Map<Integer, Message>> adjustmentLogs){
        System.out.println("Adjustment logs...");
        for (Map.Entry<ProductType, Map<Integer, Message>> entry : adjustmentLogs.entrySet()) {
            ProductType productType = entry.getKey();
            System.out.println("Adjustment logs for " + productType);
            Map<Integer, Message> messagesMap = entry.getValue();
            TreeMap<Integer, Message> orderedMap = new TreeMap<>(messagesMap);
            for (Map.Entry<Integer, Message> messagesEntry : orderedMap.entrySet()) {
                System.out.println("MessageId #" + messagesEntry.getKey() + ": " + messagesEntry.getValue().getAdjustmentReport());
            }
        }
    }

    @Override
    public void writeNotification(String message){
        System.out.println(message);
    }
}
