package com.example.ecommerce.persistence;

import com.example.ecommerce.domain.SaleDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("saleDetailDAO")
public class SaleDetailDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String INSERT_SALEDETAIL = "insert into saledatail (saledetail_id, quantity, product_idproduct, sale_idsale) values (?, ?, ?, ?)";
    private final String FIND_ALL = "select * from saledetail";
    private final String FIND_BY_SALEID = "select * from sale where idsale = ?";
    private final String UPDATE_SALEDETAIL = "update saledatil set saledetail_id = ?, quantity =?, product_idproduct=?, sale_idsale =?";
    private final String EXISTS_SALEDETAIL = "select count(*) from saledetail where sale_idsale = ?";

    public RowMapper<SaleDetail> mapper = (resultSet, i) -> {
        SaleDetail saleDetail = new SaleDetail.SaleDetailBuilder()
                .saledetail_id(resultSet.getInt("saledetail_id"))
                .quantity(resultSet.getInt("quantity"))
                .product_idProduct(resultSet.getInt("product_idproduct"))
                .sale_idSale(resultSet.getInt("sale_idsale"))
                .build();
        return saleDetail;
    };

    public SaleDetailDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<SaleDetail> findAll() {
        return jdbcTemplate.query(FIND_ALL,mapper);
    }

    public List<SaleDetail> findBySaleId (Integer idSale){
        return jdbcTemplate.query (FIND_BY_SALEID, new Object[]{idSale}, mapper);
    }

    public int insert (SaleDetail saleDetail){
        return jdbcTemplate.update (INSERT_SALEDETAIL, saleDetail.getSaledetail_id(), saleDetail.getQuantity(), saleDetail.getProduct_idProduct(), saleDetail.getSale_idSale(), mapper);
    }

    public int update (SaleDetail saleDetail){
        return jdbcTemplate.update (UPDATE_SALEDETAIL, saleDetail.getSaledetail_id(), saleDetail.getQuantity(), saleDetail.getProduct_idProduct(), saleDetail.getSale_idSale(), mapper);
    }

    public boolean existsSaleDetail (SaleDetail saleDetail){
        int countSaleDetail = jdbcTemplate.queryForObject(EXISTS_SALEDETAIL, Integer.class, saleDetail.getSale_idSale());
        return countSaleDetail > 0;
    }
}
