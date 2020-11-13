package com.example.ecommerce.service;

import com.example.ecommerce.domain.Card;
import com.example.ecommerce.domain.users.Account;
import com.example.ecommerce.domain.users.Client;

import java.util.List;

public interface IClientService {
    void insert(Account account);

    void insert(Client client);

    void update(Client client);

    void delete(Long id);

    Client findOne(Long id);

    List<Client> findAll();

    void addAddress (Client client, String address);

    void addCard (Client client, String card);

    //void setEncriptedPassword(String username, String encriptedPassword);

}
