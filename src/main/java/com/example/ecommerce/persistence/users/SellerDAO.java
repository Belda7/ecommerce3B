package com.example.ecommerce.persistence.users;

import com.example.ecommerce.configuration.SecurityDAO;
import com.example.ecommerce.domain.users.Account;
import com.example.ecommerce.domain.users.Seller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository("sellerDAO")
public class SellerDAO {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private JdbcTemplate jdbcTemplate;
    @Autowired
    private SecurityDAO securityDAO;

    public SellerDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String INSERT_SELLER="INSERT INTO SELLER (idseller, username, password, name, secondName, email, image, status, tipo  ) values (?,?,?,?,?,?,?,?,?)";
    private final String FIND_ALL="SELECT * FROM SELLER";
    private final String FIND_BY_USERNAME="SELECT * FROM SELLER WHERE username = ?";
    private final String FIND_BY_ID ="SELECT * FROM SELLER WHERE idseller = ?";
    private final String UPDATE_SELLER="UPDATE SELLER SET name = ?, secondName = ?, email = ?, image = ?, status = ?, tipo = ? where idseller = ?";
    private final String EXISTS_SELLER="select count(*) from SELLER where idseller = ?";
    private final String LAST_ID = "select max(idseller) from SELLER";

    private RowMapper<Seller> mapper = (resultSet, i) -> {
        Seller seller = new Seller();
        seller.setId(resultSet.getLong("idseller"));
        seller.setUser(resultSet.getString("username"));
        seller.setPassword(resultSet.getString("password"));
        seller.setName(resultSet.getString("name"));
        seller.setSecondName(resultSet.getString("secondName"));
        seller.setEmail(resultSet.getString("email"));
        seller.setImg(resultSet.getString("image"));
        seller.setStatus(resultSet.getString("status").equals('1')? true : false);
        seller.setType(resultSet.getString("tipo"));
        return seller;
    };

    public List<Seller> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper);
    }

    public Seller findByUsername(String username){
        return jdbcTemplate.queryForObject(FIND_BY_USERNAME, new Object[]{username} ,mapper);
    }

    public Seller findById (Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id}, mapper);
    }


    public int insert (Account account){
        Seller newClient = new Seller(account.getUser(), account.getPassword(), account.getName(), account.getSecondName(), account.getEmail());
        newClient.setId(lastID());
        String status = newClient.getStatus() ? "1" : "0";
        return jdbcTemplate.update(INSERT_SELLER, new Object[] {newClient.getId(), newClient.getUser(), newClient.getPassword(), newClient.getName(), newClient.getSecondName(), newClient.getEmail(), newClient.getImg(), status, newClient.getType().tipusCompte });
    }

    public int update (Seller seller){
        String status = seller.getStatus() ? "1" : "0";
        securityDAO.updateUser((Account) seller);
        return jdbcTemplate.update(UPDATE_SELLER, seller.getName(), seller.getSecondName(), seller.getEmail(), seller.getImg(), status, seller.getType().tipusCompte, seller.getId());
    }

    public boolean existSeller (Seller seller){
        return jdbcTemplate.queryForObject(EXISTS_SELLER, Long.class, seller.getId()) > 0;
    }

    public void delete (Seller seller){
        seller.changeStatus(false);
    }

    public boolean login(Seller sel) {
        Seller seller = this.findByUsername(sel.getUser());
        return sel.equals(seller);
    }

    private Long lastID (){
        Long id = jdbcTemplate.queryForObject(LAST_ID, Long.class);
        return id+1;
    }
}