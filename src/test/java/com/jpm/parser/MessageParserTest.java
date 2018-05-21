package com.jpm.parser;

import com.jpm.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageParserTest {

    MessageParser messageParser;

    @Before
    public void setUp(){
        messageParser = new MessageParser();
    }

    @Test
    public void shouldValidateSingleSaleMessage(){
        //given
        String singleSaleMessage = "orange at 10p";

        //when
        boolean result = messageParser.isMessageValid(singleSaleMessage);

        //then
        Assert.assertTrue(result);
    }

    @Test
    public void shouldValidateMassSalesMessage(){
        //given
        String singleSaleMessage = "20 sales of oranges at 10p each";

        //when
        boolean result = messageParser.isMessageValid(singleSaleMessage);

        //then
        Assert.assertTrue(result);
    }

    @Test
    public void shouldValidateAdjustmentMessage(){
        //given
        String adjustmentMessage = "Multiply 2p apples";

        //when
        boolean result = messageParser.isMessageValid(adjustmentMessage);

        //then
        Assert.assertTrue(result);
    }

    @Test
    public void shouldNotValidateArbitraryMessage(){
        //given
        String singleSaleMessage = "Hello world";

        //when
        boolean result = messageParser.isMessageValid(singleSaleMessage);

        //then
        Assert.assertFalse(result);
    }

    @Test
    public void shouldCreateSingleSaleObject(){
        //given
        String singleSaleMessage = "orange at 10p";

        //when
        Sale sale = messageParser.getSale(singleSaleMessage);

        //then
        Assert.assertEquals(sale.getSalePrice(), 10);
        Assert.assertEquals(sale.getSaleAmount(), 1);
        Assert.assertEquals(sale.getProductType(), ProductType.ORANGE);
    }

    @Test
    public void shouldCreateMassSaleObject(){
        //given
        String singleSaleMessage = "20 sales of oranges at 41p each";

        //when
        Sale sale = messageParser.getSale(singleSaleMessage);

        //then
        Assert.assertEquals(sale.getSalePrice(), 41);
        Assert.assertEquals(sale.getSaleAmount(), 20);
        Assert.assertEquals(sale.getProductType(), ProductType.ORANGE);
    }

    @Test
    public void shouldCreateAdjustmentObject(){
        //given
        String adjustmentMessage = "Multiply 2p apples";

        //when
        Adjustment adjustment = messageParser.getAdjustment(adjustmentMessage);

        //then
        Assert.assertEquals(adjustment.getAdjustmentType(), AdjustmentType.MULTIPLY);
        Assert.assertEquals(adjustment.getAdjustmentValue(), 2);
        Assert.assertEquals(adjustment.getProductType(), ProductType.APPLE);
    }
}
