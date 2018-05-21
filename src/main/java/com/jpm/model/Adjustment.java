package com.jpm.model;

/**
 *  This class is to represent adjustments on the sales
 */
public class Adjustment extends Message{

    private int adjustmentValue;
    private AdjustmentType adjustmentType;

    public Adjustment(Integer id, ProductType productType, int adjustmentValue, AdjustmentType adjustmentType){
        super(id, productType);
        this.adjustmentValue = adjustmentValue;
        this.adjustmentType = adjustmentType;
    }

    public int getAdjustmentValue() { return  adjustmentValue; }

    public AdjustmentType getAdjustmentType() { return adjustmentType; }

    @Override
    public String getAdjustmentReport(){
        return adjustmentType + " operation with the value: " + adjustmentValue;
    }
}
