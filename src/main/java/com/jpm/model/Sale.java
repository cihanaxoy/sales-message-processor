package com.jpm.model;

/**
 *  This class is to represent single and mass sales
 */
public class Sale extends Message {

    private int saleAmount;
    private int salePrice;

    public Sale (Integer id, ProductType productType, int saleAmount, int salePrice){
        super(id, productType);
        this.saleAmount = saleAmount;
        this.salePrice = salePrice;
    }

    public Sale (Sale sale) {
        super(sale.getId(), sale.getProductType());
        this.saleAmount = sale.getSaleAmount();
        this.salePrice = sale.getSalePrice();
    }

    public int getSaleAmount() {
        return saleAmount;
    }

    public int getSalePrice() { return salePrice; }

    public void setSalePrice(int newPrice) { this.salePrice = newPrice; }

    @Override
    public String getAdjustmentReport(){
        String sale = saleAmount == 1 ? "sale" : "sales";
        return saleAmount + " " + sale + " with the price " + salePrice;
    }
}
