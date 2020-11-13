package com.example.ecommerce.persistence;

import com.example.ecommerce.domain.Company;
import com.example.ecommerce.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("companyDAO")
public class CompanyDAO {
    private final JdbcTemplate jdbcTemplate;

    private final String INSERT_COMPANY ="INSERT INTO company (companyname, phonenumber, email, addresses, socialnetworks, seller_idsellEr) values (?,?,?,?,?,?)";
    private final String FIND_ALL ="SELECT * FROM company";
    private final String FIND_BY_IDCOMPANY ="SELECT * FROM COMPANY WHERE COMPANYNAME = ?";
    private final String UPDATE_COMPANY ="UPDATE company SET phonenumber = ?, email = ?, addresses = ?, socialnetworks = ?, seller_idseller = ? WHERE companyname = ?";
    private final String EXISTS_COMPANY ="SELECT COUNT(*) from COMPANY where seller_idseller = ?";
    private final String PRODUCTS_COMPANY = "SELECT * FROM PRODUCT WHERE company_companyname = ?";
    private final String DELETE_COMPANY = "DELETE FROM COMPANY WHERE COMPANYNAME = ?";

    public CompanyDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Company> mapper = (resultSet, i) -> {
        Company company = new Company();
        company = new Company.CompanyBuilder()
                .name(resultSet.getString("companyname"))
                .phoneNumber(resultSet.getString ("phonenumber"))
                .email(resultSet.getString("email"))
                .socialNetworks(company.toListPersist (resultSet.getString("socialnetworks")))
                .addresses(company.toListPersist(resultSet.getString("addresses")))
                .image(resultSet.getString("image"))
                .build();
        return company;
    };

    public List<Company> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper);
    }

    public List<Company> findByCompanyId (String id){
        return jdbcTemplate.query(FIND_BY_IDCOMPANY, new Object[]{id}, mapper);
    }

    public int insert (Company company) {
        return jdbcTemplate.update(INSERT_COMPANY,
                company.getName(),
                company.getPhoneNumber(),
                company.getEmail(),
                company.toStringPersist(company.getAddresses()),
                company.toStringPersist(company.getSocialNetworks()),
                company.getSellerId());
    }

    public int update (Company company){
        return jdbcTemplate.update(UPDATE_COMPANY,
                company.getPhoneNumber(),
                company.getEmail(),
                company.toStringPersist(company.getAddresses()),
                company.toStringPersist(company.getSocialNetworks()),
                company.getSellerId(),
                company.getName());
    }

    public boolean existsCompany (Company company){
        int countCompany = jdbcTemplate.queryForObject(EXISTS_COMPANY, Integer.class, company.getName());
        return countCompany > 0;
    }

    public List<Product> getAllProductsFromCompany(String id) {
        return jdbcTemplate.query(PRODUCTS_COMPANY, new Object[]{id}, ProductDAO.mapper);
    }

    public int delete(Company company) {
        return jdbcTemplate.update(DELETE_COMPANY, company.getName());
    }
}

