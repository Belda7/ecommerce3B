package com.example.ecommerce.service;

import com.example.ecommerce.domain.users.Account;
import com.example.ecommerce.domain.users.Seller;

import java.util.List;

public interface ISellerService {

    void insert(Account account);

    void insert(Seller seller);

    void update(Seller seller);

    void delete(Long id);

    Seller findOne(Long id);

    List<Seller> findAll();

    Seller findByUsername(String username);

    Seller existsClient(Long id);

    boolean login(Seller seller);

    //void setEncriptedPassword(String username, String encriptedPassword);
}
