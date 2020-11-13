package com.example.ecommerce.service;

import com.example.ecommerce.configuration.SecurityDAO;
import com.example.ecommerce.domain.users.Account;
import com.example.ecommerce.domain.users.Client;
import com.example.ecommerce.persistence.users.ClientDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("clientService")
public class ClientServiceImpl implements IClientService{

    private ClientDAO clientDAO;
    private SecurityDAO securityDAO;

    public ClientServiceImpl(ClientDAO clientDAO, SecurityDAO securityDAO) {
        this.clientDAO = clientDAO;
        this.securityDAO = securityDAO;
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    public void insert(Account account) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        account.setPassword(encoder.encode(account.getPassword()));
        clientDAO.insert(account);
        securityDAO.createUserAndRole(account);
        log.info("CLIENT: "+ account.getUser() + "added.");
        log.info("CLIENT: " + account.toString());
    }

    @Override
    public void insert(Client client) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        client.setPassword(encoder.encode(client.getPassword()));
        clientDAO.insert(client);
        log.info("CLIENT: "+ client.getUser() + "added.");
        log.info("CLIENT: " + client.toString());
    }

    @Override
    public void update(Client client) {
        clientDAO.update(client);
        log.info("CLIENT: " + client.toString() + "updated.");
        log.info("CLIENT: " + client.toString());
    }

    @Override
    public void delete(Long id) {
        Client client = clientDAO.findById(id);
        clientDAO.delete(client);
        log.info("CLIENT: " + client.getUser() + "deleted.");
    }

    @Override
    public Client findOne(Long id) {
        return clientDAO.findById(id);
    }

    @Override
    public List<Client> findAll() {
        return clientDAO.findAll();
    }


    public void addAddress(Client client, String address) {
        client.addAddress(address);
        clientDAO.update(client);
    }

    public void addCard(Client client, String card) {
        client.addCard(card);
    }

    /*
    @Override
    public void setEncriptedPassword(String username, String encriptedPassword) {
        Client client = clientDAO.findByUsername(username);
        client.setPassword(encriptedPassword);
        clientDAO.update(client);
    }*/
}
