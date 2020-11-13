package com.example.ecommerce.domain;

import java.util.Date;
import java.util.Objects;

// TARJETA
public class Card {

    private String cardNumber;
    private String owner;
    private Date expireDate;
    private int idClient;

    public Card(CardBuilder builder) {
        setIdClient(builder.idClient);
        setCardNumber(builder.cardNumber);
        setOwner(builder.owner);
        setExpireDate(builder.expireDate);
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        Card card = (Card) o;
        return getCardNumber().equals(card.getCardNumber()) &&
                getExpireDate().equals(card.getExpireDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardNumber(), getExpireDate());
    }

    public static class CardBuilder {
        private String cardNumber;
        private String owner;
        private Date expireDate;
        private int idClient;

        public CardBuilder cardNumber (String cardNumber){
            this.cardNumber = cardNumber;
            return this;
        }

        public CardBuilder owner (String owner){
            this.owner = owner;
            return this;
        }

        public CardBuilder expireDate (Date expireDate){
            this.expireDate = expireDate;
            return this;
        }

        public CardBuilder idClient (int idClient){
            this.idClient = idClient;
            return this;
        }

        public Card build () {
            return new Card(this);
        }
    }
}