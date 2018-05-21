package com.jpm.service;

import com.jpm.config.Configurations;
import com.jpm.dao.Memory;
import com.jpm.dao.Persistence;
import com.jpm.io.destination.ConsoleWriter;
import com.jpm.io.destination.ReportWriterListener;
import com.jpm.model.ProductType;
import com.jpm.model.Sale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceTest {

    Persistence persistence;
    ReportWriterListener reportWriterListener;

    MessageService messageService;

    @Before
    public void setUp(){

        Configurations.load();

        persistence = new Memory();
        reportWriterListener = new ConsoleWriter();

        List<ReportWriterListener> reportWriterListeners = new ArrayList<>();
        reportWriterListeners.add(reportWriterListener);

        messageService = new MessageService(persistence, reportWriterListeners);
    }

    @Test
    public void shouldProcessAndPersistAndPrintMessage(){
        //given
        List<String> messages = new ArrayList<>();
        messages.add("orange at 10p");
        messages.add("apple at 20p");
        messages.add("20 sales of oranges at 41p each");
        messages.add("Add 20p apples");
        messages.add("orange at 10p");
        messages.add("Multiply 2p oranges");
        messages.add("Multiply 2p apples");
        messages.add("20 sales of apples at 10p each");
        messages.add("orange at 30p");
        messages.add("apple at 40p");

        //when
        messages.stream().forEach(message->messageService.onMessageReceived(message));

        //then
        Map<ProductType, List<Sale>> salesMap = persistence.getSales();

        List<Sale> appleSales = salesMap.get(ProductType.APPLE);
        int appleTotalPrice = 0;
        int appleTotalSale = 0;
        for(Sale sale : appleSales) {
            appleTotalPrice += sale.getSalePrice();
            appleTotalSale += sale.getSaleAmount();
        }

        Assert.assertEquals(appleTotalPrice, 130);
        Assert.assertEquals(appleTotalSale, 22);
    }
}
