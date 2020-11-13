package com.example.ecommerce;

import com.example.ecommerce.configuration.SecurityDAO;

import com.example.ecommerce.configuration.SecurityDAO;
import com.example.ecommerce.persistence.*;
import com.example.ecommerce.persistence.users.AdminDAO;
import com.example.ecommerce.persistence.users.ClientDAO;
import com.example.ecommerce.persistence.users.SellerDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApplication {

    private SecurityDAO securityDAO;
    private CardDAO cardDAO;
    private CategoryDAO categoryDAO;
    private CompanyDAO companyDAO;
    private ProductDAO productDAO;
    private SaleDAO saleDAO;
    private SaleDetailDAO saleDetailDAO;
    private AdminDAO adminDAO;
    private ClientDAO clientDAO;
    private SellerDAO sellerDAO;

    public EcommerceApplication(SecurityDAO securityDAO, CardDAO cardDAO, CategoryDAO categoryDAO, CompanyDAO companyDAO, ProductDAO productDAO, SaleDAO saleDAO, SaleDetailDAO saleDetailDAO, AdminDAO adminDAO, ClientDAO clientDAO, SellerDAO sellerDAO) {
        this.securityDAO = securityDAO;
        this.cardDAO = cardDAO;
        this.categoryDAO = categoryDAO;
        this.companyDAO = companyDAO;
        this.productDAO = productDAO;
        this.saleDAO = saleDAO;
        this.saleDetailDAO = saleDetailDAO;
        this.adminDAO = adminDAO;
        this.clientDAO = clientDAO;
        this.sellerDAO = sellerDAO;
    }

    public static void main(String[] args) {
        SpringApplication.run(EcommerceApplication.class, args);
    }

}
