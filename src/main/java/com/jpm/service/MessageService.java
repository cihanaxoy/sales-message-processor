package com.jpm.service;

import com.jpm.config.Configurations;
import com.jpm.dao.Persistence;
import com.jpm.io.destination.MessageReceivedListener;
import com.jpm.io.destination.ReportWriterListener;
import com.jpm.io.source.MessageSource;
import com.jpm.model.*;
import com.jpm.parser.MessageParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  This is the main business class, it is registered to message sources
 *  It has a persistence composition and report output channels
 *  Each time a message is reached, it's processed, persisted and delivered
 */
public class MessageService implements MessageReceivedListener{

    private Persistence persistence;

    private List<ReportWriterListener> reportDestinations;

    private List<MessageSource> messageSources;

    private MessageParser messageParser;

    private int messageCounter = 0;

    private int salesLogPeriod, messageLimitAmount;

    public MessageService(Persistence persistence, List<ReportWriterListener> reportDestinations){

        this.persistence = persistence;
        this.reportDestinations = reportDestinations;

        messageSources = new ArrayList<>();

        messageParser = new MessageParser();

        salesLogPeriod = Integer.parseInt(Configurations.map.getProperty("salesLogPeriod"));
        messageLimitAmount = Integer.parseInt(Configurations.map.getProperty("messageLimitAmount"));
    }

    public void addMessageSource(MessageSource messageSource){
        messageSources.add(messageSource);
    }

    @Override
    public void onMessageReceived(String message) {

        if(messageParser.isMessageValid(message)){
            messageCounter++;

            // parse messages and persist
            if(messageParser.isAdjustmentMessage(message)){
                Adjustment adjustment = messageParser.getAdjustment(message);
                adjustment.setId(messageCounter);

                // update previous sales based on the adjustment
                Map<ProductType, List<Sale>> salesMap = persistence.getSales();
                List<Sale> salesByProduct = salesMap.get(adjustment.getProductType());
                salesByProduct.stream().forEach(sale->{
                    if(adjustment.getAdjustmentType() == AdjustmentType.ADD){
                        sale.setSalePrice(sale.getSalePrice() + adjustment.getAdjustmentValue());
                    } else if(adjustment.getAdjustmentType() == AdjustmentType.SUBTRACT){
                        sale.setSalePrice(sale.getSalePrice() - adjustment.getAdjustmentValue());
                    } else if(adjustment.getAdjustmentType() == AdjustmentType.MULTIPLY){
                        sale.setSalePrice(sale.getSalePrice() * adjustment.getAdjustmentValue());
                    }
                });

                persistence.updateSalesByProductType(adjustment.getProductType(), salesByProduct);
                persistence.saveAdjustment(adjustment);
            } else {
                Sale sale = messageParser.getSale(message);
                sale.setId(messageCounter);
                persistence.saveSale(sale);

                Sale saleClone = new Sale(sale);
                persistence.saveAdjustment(saleClone);
            }

            // send the sale logs for each 10 messages
            if(messageCounter % salesLogPeriod == 0){
                Map<ProductType, List<Sale>> sales = persistence.getSales();
                reportDestinations.stream().forEach(destination->{
                    destination.writeNotification("Sales logs after " + messageCounter + "th sales...");
                    destination.writeSalesLogs(sales);
                });
            }

            // complete the process and send the adjustment logs at messageLimitAmount
            if(messageCounter == messageLimitAmount){

                messageSources.stream().forEach(messageSource -> {
                    messageSource.unregisterMessageListener(this);
                });

                Map<ProductType, Map<Integer, Message>> adjustments = persistence.getAdjustments();
                reportDestinations.stream().forEach(destination->{
                    destination.writeNotification("Memory came up to the limit, no more message will be accepted...");
                    destination.writeAdjustmentLogs(adjustments);
                });
            }
        } else{
            System.out.println(3);
        }
    }
}
