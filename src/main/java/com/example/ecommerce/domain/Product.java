package com.example.ecommerce.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Product implements Image {
    @NotNull
    private int idProduct;

    @Size (min = 2, max = 100, message = "The size of the name must be between 2 and 100 characters.")
    private String name;

    @Size (max = 5000, message = "The size of the description cannot be longer than 5000 characters.")
    private String description;

    @NotNull
    private Double price;
    private int stock;
    private String companyName;
    private int promotion;
    private String categoryName;
    private boolean visible;
    private String image;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visibility) {
        this.visible = visibility;
    }


    public Product() { }

    public Product (ProductBuilder builder){
        setIdProduct(builder.idProduct);
        setName(builder.name);
        setDescription(builder.description);
        setPrice(builder.price);
        setStock(builder.stock);
        setCompany(builder.companyName);
        setCategory(builder.categoryName);
        setVisible(builder.visibility);
        setImage(builder.image);
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getCompany() {
        return companyName;
    }

    public void setCompany(String companyName) {
        this.companyName = companyName;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public String getCategory() {
        return categoryName;
    }

    public void setCategory(String category) {
        this.categoryName = category;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        return getIdProduct();
    }

    @Override
    public boolean equals(Object o) {
        return o.hashCode() == this.hashCode();
    }

    public static class ProductBuilder {
        private int idProduct;
        private String name;
        private String description;
        private Double price;
        private int stock;
        private String companyName;
        private int promotion;
        private boolean visibility;
        private String categoryName;
        private String image;

        public ProductBuilder image(String image) {
            this.image = image;
            return this;
        }

        public ProductBuilder visibility(boolean visibility) {
            this.visibility = visibility;
            return this;
        }

        public ProductBuilder idProduct(int idProduct){
            this.idProduct = idProduct;
            return this;
        }

        public ProductBuilder name (String name){
            this.name = name;
            return this;
        }

        public ProductBuilder description (String description){
            this.description = description;
            return this;
        }

        public ProductBuilder price (Double price){
            this.price = price;
            return this;
        }

        public ProductBuilder stock (int stock){
            this.stock = stock;
            return this;
        }

        public ProductBuilder company (String companyName){
            this.companyName = companyName;
            return this;
        }

        public ProductBuilder promotion (int promotion){
            this.promotion = promotion;
            return this;
        }

        public ProductBuilder category (String categoryName){
            this.categoryName = categoryName;
            return this;
        }

        public Product build(){
            return new Product(this);
        }
    }
}
