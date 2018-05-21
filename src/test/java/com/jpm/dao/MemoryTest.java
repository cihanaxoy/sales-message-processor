package com.jpm.dao;

import com.jpm.model.Adjustment;
import com.jpm.model.AdjustmentType;
import com.jpm.model.ProductType;
import com.jpm.model.Sale;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MemoryTest {

    Persistence persistence;

    @Before
    public void setUp(){
        persistence = new Memory();
    }

    @Test
    public void shouldSaveSale(){
        //given
        Sale sale = new Sale(100, ProductType.ORANGE, 10, 25);

        //when
        persistence.saveSale(sale);

        //then
        Assert.assertEquals(persistence.getSales().size(), 1);
    }

    @Test
    public void shouldSaveAdjustment(){
        //given
        Adjustment adjustment = new Adjustment(101, ProductType.ORANGE, 2, AdjustmentType.MULTIPLY);

        //when
        persistence.saveAdjustment(adjustment);

        //then
        Assert.assertEquals(persistence.getAdjustments().size(), 1);
    }

    @Test
    public void shouldUpdateSales(){
        //given
        Sale sale1 = new Sale(100, ProductType.ORANGE, 10, 25);
        Sale sale2 = new Sale(101, ProductType.ORANGE, 5, 25);
        List<Sale> sales = new ArrayList<>();
        sales.add(sale1);
        sales.add(sale2);
        sales.stream().forEach(sale -> persistence.saveSale(sale));

        //when
        Map<ProductType, List<Sale>> salesMockDb = persistence.getSales();
        List<Sale> orangeSales = salesMockDb.get(ProductType.ORANGE);
        orangeSales.stream().forEach(orangeSale->orangeSale.setSalePrice(orangeSale.getSalePrice()*2));
        persistence.updateSalesByProductType(ProductType.ORANGE, orangeSales);

        //then
        Map<ProductType, List<Sale>> updatedSalesMockDb = persistence.getSales();
        List<Sale> updatedSales = updatedSalesMockDb.get(ProductType.ORANGE);
        updatedSales.stream().forEach(updatedSale->{
            Assert.assertEquals(updatedSale.getSalePrice(), 50);
        });
    }
}
