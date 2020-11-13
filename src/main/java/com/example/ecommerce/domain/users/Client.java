package com.example.ecommerce.domain.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

// CLIENTE
public class Client extends Account implements Serializable {

    private List<String> address;
    private String phoneNumber;
    private String img;
    private List<String> cards;

    public Client() {
    }

    public Client (Account account) {
        super(account.getUser(), account.getPassword(), account.getName(), account.getSecondName(), account.getEmail());
        this.address = new ArrayList<>();
        this.setType(AccountType.CLIENT);
        this.img = null;
    }
    public void addAddress (String street){
        if (!address.contains(street)) {
            this.address.add(street);
        }
    }

    public Client(String username, String password, String name, String secondName, String email) {
        super(username, password, name, secondName, email);
        this.address = new ArrayList<String>();
        this.changeStatus(true);
        this.setType(AccountType.CLIENT);
    }

    public void removeAddress (String street){
        if (address.contains(street)) {
            this.address.remove(street);
        }
    }

    public void addCard (String card){
        if (!cards.contains(card)) {
            this.cards.add(card);
        }
    }

    public void removeCard (String card) {
        if (cards.contains(card)) {
            this.cards.remove(card);
        }
    }

    public String toStringAddress(){
        Iterator<String> it = this.address.iterator();
        String str = "";
        while (it.hasNext()) {
            str = str + "_";
        }
        return str;
    };

    public List<String> toListAddress(String str){
        List<String> address = new ArrayList<String>();
        String[] s = str.split("_");
        for (String st: s) {
            address.add(st);
        }
        return address;
    };

    public List<String> getAddress() {
        return address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public void setAddress(String address) {
        this.address = toListAddress(address);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void changeStatus (Boolean status) {
        this.setStatus(status);
    }

    public void setType (String type) {
        this.setType(AccountType.getTipusCompte(type));
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUser(), this.getPassword());
    }

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        String str = super.toString();
        str = str +  "Client{" +
                "address=" + address +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", img='" + img + '\'' +
                ", cards=" + cards +
                '}';
        return str;
    }

}