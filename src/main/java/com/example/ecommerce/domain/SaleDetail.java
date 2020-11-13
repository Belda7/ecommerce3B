package com.example.ecommerce.domain;

public class SaleDetail {
    private int saledetail_id;
    private int quantity;
    private int product_idProduct;
    private int sale_idSale;

    public SaleDetail (SaleDetailBuilder builder){
        setSaledetail_id(builder.saledetail_id);
        setQuantity(builder.quantity);
        setProduct_idProduct(builder.product_idProduct);
        setSale_idSale(builder.sale_idSale);
    }

    public int getSaledetail_id() {
        return saledetail_id;
    }

    public void setSaledetail_id(int saledetail_id) {
        this.saledetail_id = saledetail_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduct_idProduct() {
        return product_idProduct;
    }

    public void setProduct_idProduct(int product_idProduct) {
        this.product_idProduct = product_idProduct;
    }

    public int getSale_idSale() {
        return sale_idSale;
    }

    public void setSale_idSale(int sale_idSale) {
        this.sale_idSale = sale_idSale;
    }

    public static class SaleDetailBuilder {
        private int saledetail_id;
        private int quantity;
        private int product_idProduct;
        private int sale_idSale;

        public SaleDetailBuilder saledetail_id (int saledetail_id){
            this.saledetail_id = saledetail_id;
            return this;
        }

        public SaleDetailBuilder quantity (int quantity){
            this.quantity = quantity;
            return this;
        }

        public SaleDetailBuilder product_idProduct (int product_idProduct){
            this.product_idProduct = product_idProduct;
            return this;
        }

        public SaleDetailBuilder sale_idSale (int sale_idSale){
            this.sale_idSale = sale_idSale;
            return this;
        }

        public SaleDetail build() {
            return new SaleDetail(this);
        }
    }
}
