package com.example.ecommerce.domain;

import java.util.Objects;

// VENTA
public class Sale {

    public enum paymentMethods {CREDITCARD, PAYPAL, CASH}

    private int idSale;
    private paymentMethods pay;
    private Double amount;
    private int client_idClient;

    public Sale(SaleBuilder builder) {
        setIdSale(builder.idSale);
        setPay(builder.pay);
        setAmount(builder.amount);
        setClient_idClient(builder.client_idClient);
    }

    public int getIdSale() {
        return idSale;
    }

    public void setIdSale(int idSale) {
        this.idSale = idSale;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public paymentMethods getPay() {
        return pay;
    }

    public void setPay(paymentMethods pay) {
        this.pay = pay;
    }

    public Double getAmount() {
        return amount;
    }

    public int getClient_idClient() {
        return client_idClient;
    }

    public void setClient_idClient(int client_idClient) {
        this.client_idClient = client_idClient;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sale)) return false;
        Sale sale = (Sale) o;
        return getIdSale() == sale.getIdSale();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdSale());
    }

    public static class SaleBuilder {
        private int idSale;
        private paymentMethods pay;
        private Double amount;
        private int client_idClient;

        public SaleBuilder idSale (int idSale) {
            this.idSale = idSale;
            return this;
        }

        public SaleBuilder pay (paymentMethods pay){
            this.pay = pay;
            return this;
        }

        public SaleBuilder amount (Double amount){
            this.amount = amount;
            return this;
        }

        public SaleBuilder client_idClient (int client_idClient){
            this.client_idClient = client_idClient;
            return this;
        }

        public Sale build() {
            return new Sale (this);
        }
    }
}
