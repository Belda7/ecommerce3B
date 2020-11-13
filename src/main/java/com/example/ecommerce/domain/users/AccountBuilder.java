package com.example.ecommerce.domain.users;

public class AccountBuilder {
    public Long id;
    public String name;
    public String secondName;
    public String user;
    public String password;
    public boolean status; // true -> active account
    public String email;
    public AccountType type;
    public String image;

    public AccountBuilder image(String image) {
        this.image = image;
        return this;
    }

    public AccountBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public AccountBuilder name(String name) {
        this.name = name;
        return this;
    }

    public AccountBuilder secondName(String secondName) {
        this.secondName = secondName;
        return this;
    }

    public AccountBuilder user(String user) {
        this.user = user;
        return this;
    }

    public AccountBuilder password(String password) {
        this.password = password;
        return this;
    }

    public AccountBuilder status(boolean status) {
        this.status = status;
        return this;
    }

    public AccountBuilder email(String email) {
        this.email = email;
        return this;
    }

    public AccountBuilder type(String type) {
        this.type = AccountType.getTipusCompte(type);
        return this;
    }

    public Account build() {
        return new Account(this);
    }
}
