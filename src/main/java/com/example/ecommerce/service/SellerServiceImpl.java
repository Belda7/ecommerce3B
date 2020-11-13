package com.example.ecommerce.service;

import com.example.ecommerce.configuration.SecurityDAO;
import com.example.ecommerce.domain.users.Account;
import com.example.ecommerce.domain.users.Seller;
import com.example.ecommerce.persistence.users.SellerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("sellerService")
public class SellerServiceImpl implements ISellerService{

    private SellerDAO sellerDAO;
    private SecurityDAO securityDAO;

    public SellerServiceImpl(SellerDAO sellerDAO, SecurityDAO securityDAO) {
        this.sellerDAO = sellerDAO;
        this.securityDAO = securityDAO;
    }

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Override
    public void insert(Account account) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        account.setPassword(encoder.encode(account.getPassword()));
        sellerDAO.insert(account);
        securityDAO.createUserAndRole(account);
        log.info("SELLER: " + account.getUser() + " added.");
        log.info("SELLER: " + account.toString());
    }

    @Override
    public void insert(Seller seller) {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        seller.setPassword(encoder.encode(seller.getPassword()));
        sellerDAO.insert(seller);
        log.info("SELLER: " + seller.getUser() + " added.");
        log.info("SELLER: " + seller.toString());
    }

    @Override
    public void update(Seller seller) {
        log.info("SELLER: " + seller.toString() + "updated.");
        sellerDAO.update(seller);
    }

    @Override
    public Seller findOne(Long id) {
        return sellerDAO.findById(id);
    }

    @Override
    public List<Seller> findAll() {
        return sellerDAO.findAll();
    }

    @Override
    public Seller findByUsername(String username) {
        return sellerDAO.findByUsername(username);
    }

    @Override
    public Seller existsClient(Long id) {
        return sellerDAO.findById(id);
    }

    @Override
    public boolean login(Seller client) {
        return sellerDAO.login(client);
    }


    /*@Override
    public void setEncriptedPassword(String username, String encriptedPassword) {
        Seller seller = sellerDAO.findByUsername(username);
        seller.setPassword(encriptedPassword);
        sellerDAO.update(seller);
    }*/


    @Override
    public void delete(Long id) {
        Seller seller = sellerDAO.findById(id);
        sellerDAO.delete(seller);
        log.info("SELLER: " + seller.getUser() + "deleted.");
    }
}
