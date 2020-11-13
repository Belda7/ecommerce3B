package com.example.ecommerce.domain.users;

import com.example.ecommerce.domain.Image;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

// CUENTA
 public class Account implements Serializable, Image {


    private Long id;

    @NotEmpty
    private String name;

    private String secondName;

    @NotNull
    private String username;

    @NotNull
    private String password;
    private boolean status; // true -> active account

    @NotNull
    @Email
    private String email;

    private AccountType type;
    private String image;
    private String role;
    //private Authority authority;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Account() {
        this.status = true;
        this.type = AccountType.CLIENT;
    }

    public Account(String username, String password, String name, String secondName, String email) {
        this.name = name;
        this.secondName = secondName;
        this.username = username;
        this.password = password;
        this.status = true;
        this.email = email;
        this.type = AccountType.CLIENT;
    }

    public Account(AccountBuilder builder) {
        setId(builder.id);
        setEmail(builder.email);
        setName(builder.name);
        setPassword(builder.password);
        setSecondName(builder.name);
        setStatus(builder.status);
        setImage(builder.image);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return username;
    }

    public void setUser(String user) {
        this.username = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getUser().equals(account.getUser()) &&
                getEmail().equals(account.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUser(), getEmail());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", email='" + email + '\'' +
                ", type=" + type +
                '}';
    }

}


