package com.jpm.model;

/**
 *  This class is to represent an abstract form for all types of messages
 */
public abstract class Message {

    private Integer id;
    private ProductType productType;

    public Message(){
    }

    public Message(Integer id, ProductType productType){
        this.id = id;
        this.productType = productType;
    }

    public void setId(Integer id) { this.id = id; }

    public Integer getId() { return id; }

    public ProductType getProductType() {
        return productType;
    }

    public abstract String getAdjustmentReport();
}
