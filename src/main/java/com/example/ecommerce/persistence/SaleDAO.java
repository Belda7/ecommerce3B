package com.example.ecommerce.persistence;

import com.example.ecommerce.domain.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("saleDAO")
public class SaleDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String INSERT_SALE = "INSERT INTO sale (pay, amount, client_idClient) VALUES (?,?,?)";
    private final String FIND_ALL = "select * from sale";
    private final String FIND_BY_SALEID = "select * from sale where idsale = ?";
    private final String UPDATE_SALE = "UPDATE sale set pay = ?, amount = ?, client_idclient = ? ";
    private final String EXISTS_SALE = "select count(*) from sale where client_idclient = ?";

    public RowMapper<Sale> mapper = (resultSet, i) -> {
        Sale sale = new Sale.SaleBuilder()
                .idSale(resultSet.getInt("idSale"))
                .pay((Sale.paymentMethods) resultSet.getObject("pay"))
                .amount(resultSet.getDouble("amount"))
                .client_idClient(resultSet.getInt("client_idclient"))
                .build();
        return sale;
    };

    public SaleDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Sale> findAll(){
        return jdbcTemplate.query (FIND_ALL, mapper);
    }

    public List<Sale> findBySaleId (Integer idSale){
        return jdbcTemplate.query (FIND_BY_SALEID, new Object[]{idSale}, mapper);
    }

    public int insert (Sale sale){
        return jdbcTemplate.update(INSERT_SALE, sale.getIdSale(), sale.getPay(), sale.getAmount(), sale.getClient_idClient());
    }

    public int update (Sale sale){
        return jdbcTemplate.update(UPDATE_SALE, sale.getIdSale(), sale.getPay(), sale.getAmount(), sale.getClient_idClient());
    }

    public boolean existsSale (Sale sale){
        int countSale = jdbcTemplate.queryForObject(EXISTS_SALE, Integer.class, sale.getIdSale());
        return countSale > 0;
    }




}
