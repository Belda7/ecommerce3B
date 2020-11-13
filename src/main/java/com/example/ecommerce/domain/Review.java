package com.example.ecommerce.domain;

// RESEÑAS / COMENTARIOS
public class Review {
    private String content;
    private int grade;

    public Review() { }

    public Review(ReviewBuilder builder) {
        setContent(builder.content);
        setGrade(builder.grade);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public static class ReviewBuilder {
        private String content;
        private int grade;

        public ReviewBuilder content(String content) {
            this.content = content;
            return this;
        }

        public ReviewBuilder grade(int grade) {
            this.grade = grade;
            return this;
        }

        public Review build() {
            return new Review(this);
        }
    }

}
