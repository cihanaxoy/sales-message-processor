package com.jpm.parser;

import com.jpm.model.Adjustment;
import com.jpm.model.AdjustmentType;
import com.jpm.model.ProductType;
import com.jpm.model.Sale;

import java.util.List;

/**
 *  This class is to parse and detect the messages based on regex rules
 */
public class MessageParser {

    private final String regexForOneSale = "(\\w+)\\sat\\s(\\d+)p$";
    private final String regexForBulkSale = "(\\d+)\\ssales\\sof\\s(\\w+)s\\sat\\s(\\d+)p\\seach$";
    private final String regexForAdjustment = "(Add|Subtract|Multiply)\\s(\\d+)p?\\s(\\w+)s$";

    public MessageParser(){

    }

    public boolean isMessageValid(String message){
        if(Regex.isRegexMatch(regexForOneSale, message) ||
            Regex.isRegexMatch(regexForBulkSale, message) ||
            Regex.isRegexMatch(regexForAdjustment, message)){

            return true;
        }
        return false;
    }

    public boolean isAdjustmentMessage(String message){
        if(Regex.isRegexMatch(regexForAdjustment, message)){

            return true;
        }
        return false;
    }

    public Sale getSale(String message){

        Sale sale = null;
        if(Regex.isRegexMatch(regexForOneSale, message)){
            List<String> tokens = Regex.getRegexTokens(regexForOneSale, message);
            sale = new Sale(-1, ProductType.valueOf(tokens.get(1).toUpperCase()),
                    1, Integer.parseInt(tokens.get(2)));
        } else{
            List<String> tokens = Regex.getRegexTokens(regexForBulkSale, message);
            sale = new Sale(-1, ProductType.valueOf(tokens.get(2).toUpperCase()),
                    Integer.parseInt(tokens.get(1)), Integer.parseInt(tokens.get(3)));
        }
        return sale;
    }

    public Adjustment getAdjustment(String message){
        Adjustment adjustment = null;
        List<String> tokens = Regex.getRegexTokens(regexForAdjustment, message);
        adjustment = new Adjustment(-1, ProductType.valueOf(tokens.get(3).toUpperCase()),
                Integer.parseInt(tokens.get(2)), AdjustmentType.valueOf(tokens.get(1).toUpperCase()));
        return adjustment;
    }
}
