package com.example.ecommerce.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// EMPRESA
public class Company implements Addresses, Image {

    private String name;
    private String phoneNumber;
    private String email;
    private int sellerId;
    private String image;
    private List<String> addresses = new ArrayList<>();
    private List<String> socialNetworks = new ArrayList<>();

    public Company() { }

    public Company(CompanyBuilder builder) {
        setSellerId(builder.sellerId);
        setName(builder.name);
        setPhoneNumber(builder.phoneNumber);
        setEmail(builder.email);
        setSocialNetworks(builder.socialNetworks);
        setAddresses(builder.addresses);
        setImage(builder.image);
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getSocialNetworks() {
        return socialNetworks;
    }

    public void setSocialNetworks(List<String> socialNetworks) {
        this.socialNetworks = socialNetworks;
    }

    @Override
    public List<String> getAddresses() {
        return addresses;
    }

    @Override
    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toStringPersist(List<String> list) {
        Iterator<String> it = list.iterator();
        String str = "";
        while (it.hasNext()) {
            str = str + it.next() + "_";
        }
        return str;
    }

    @Override
    public List<String> toListPersist(String str){
        List<String> list = new ArrayList<>();
        String[] s = str.split("_");
        for (String st: s) {
            list.add(st);
        }
        return list;
    }

    public static class CompanyBuilder {
        private String name;
        private String phoneNumber;
        private String email;
        private int sellerId;
        private String image;
        private List<String> addresses = new ArrayList<>();
        private List<String> socialNetworks = new ArrayList<>();

        public CompanyBuilder name (String companyName){
            this.name = companyName;
            return this;
        }

        public CompanyBuilder phoneNumber (String phoneNumber){
            this.phoneNumber = phoneNumber;
            return this;
        }

        public CompanyBuilder email (String email){
            this.email = email;
            return this;
        }

        public CompanyBuilder sellerId(int sellerId) {
            this.sellerId = sellerId;
            return this;
        }

        public CompanyBuilder image(String image) {
            this.image = image;
            return this;
        }

        public CompanyBuilder addresses(List<String> addresses) {
            this.addresses = addresses;
            return this;
        }

        public CompanyBuilder socialNetworks(List<String> socialNetworks) {
            this.socialNetworks = socialNetworks;
            return this;
        }

        public Company build(){
            return new Company(this);
        }
    }
}
