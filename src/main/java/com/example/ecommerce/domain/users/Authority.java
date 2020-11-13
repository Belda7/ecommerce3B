package com.example.ecommerce.domain.users;

public class Authority {

    private Account account;
    private String authority;

    public Authority() {
        this.account = new Account();
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
