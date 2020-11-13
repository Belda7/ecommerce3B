package com.example.ecommerce.domain.users;


import java.io.Serializable;
import java.util.Objects;

// VENDEDOR
public class Seller extends Account implements Serializable {

    private String img;

    public Seller() {
    }

    public Seller (Account account) {
        super(account.getUser(), account.getPassword(), account.getName(), account.getSecondName(), account.getEmail());
        this.setType(AccountType.SELLER);
        this.img = null;
    }

    public Seller(String username, String password, String name, String secondName, String email) {
        super(username, password, name, secondName, email);
        this.setType(AccountType.SELLER);
        this.setImg(null);
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void changeStatus (Boolean status) {
        this.setStatus(status);
    }

    public void setType (String type) {
        this.setType(AccountType.getTipusCompte(type));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUser(), this.getPassword());
    }

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return super.toString();
    }
}
