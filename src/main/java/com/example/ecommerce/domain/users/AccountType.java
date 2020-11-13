package com.example.ecommerce.domain.users;

public enum AccountType {
    CLIENT ("cliente"), SELLER ("Seller"), ADMIN ("Admin");

    public final String tipusCompte;

    AccountType(String tipusCompte) {
        this.tipusCompte = tipusCompte;
    }

    public static AccountType getTipusCompte (String tCompte) {
        switch (tCompte) {
            case "cliente" : return CLIENT;
            case "Seller" : return SELLER;
            case "Admin" : return ADMIN;
            default: return null;
        }
    }
}
