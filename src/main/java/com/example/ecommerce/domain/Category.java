package com.example.ecommerce.domain;

// CATEGORIAS / TAGS
public class Category implements Image {

    private String nameCategory;
    private String image;

    public Category() {}

    public Category(CategoryBuilder builder) {
        setNameCategory(builder.nameCategory);
        setImage(builder.image);
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

    public static class CategoryBuilder {
        private String nameCategory;
        private String image;

        public CategoryBuilder image(String image) {
            this.image = image;
            return this;
        }

        public CategoryBuilder nameCategory (String nameCategory){
            this.nameCategory = nameCategory;
            return this;
        }

        public Category build() { return new Category (this); }
    }
}
