package com.example.ecommerce.persistence.users;

import com.example.ecommerce.configuration.SecurityDAO;
import com.example.ecommerce.domain.users.Account;
import com.example.ecommerce.domain.users.AccountBuilder;
import com.example.ecommerce.domain.users.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("adminDAO")
public class AdminDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private SecurityDAO securityDAO;

    private final String INSERT_ADMIN="INSERT INTO ADMIN (name, secondName, username, password, email ) values (?,?,?,?,?)";
    private final String FIND_ALL="SELECT * FROM ADMIN";
    private final String FIND_BY_USERNAME="SELECT * FROM ADMIN WHERE username = ?";
    private final String FIND_BY_ID ="SELECT * FROM ADMIN WHERE idadmin = ?";
    private final String UPDATE_ADMIN="UPDATE ADMIN SET name = ?, secondName = ?, username = ?, password = ?, email = ?";
    private final String EXISTS_ADMIN="select count(*) from ADMIN where idadmin = ?";

    private RowMapper<Admin> mapper = (resultSet, i) -> {
        Admin client = (Admin) new AccountBuilder().user(resultSet.getString("user"))
                .password(resultSet.getString("password"))
                .name(resultSet.getString("name"))
                .secondName(resultSet.getString("secondName"))
                .email(resultSet.getString("email"))
                .id(resultSet.getLong("idadmin"))
                .type(resultSet.getString("type"))
                .image(resultSet.getString("image"))
                .status(resultSet.getBoolean("status"))
                .build();
        return client;
    };

    public AdminDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Admin> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper);
    }

    public List<Admin> findByUsername(String username){
        return jdbcTemplate.query(FIND_BY_USERNAME, new Object[]{username} ,mapper);
    }

    public List<Admin> findById (int id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, mapper);
    }

    public int insert (Admin admin){
        securityDAO.updateUser((Account) admin);
        return jdbcTemplate.update(INSERT_ADMIN, admin.getUser(), admin.getPassword(), admin.getName(), admin.getSecondName(), admin.getEmail());
    }

    public int update (Admin admin){
        securityDAO.updateUser((Account) admin);
        return jdbcTemplate.update(UPDATE_ADMIN, admin.getName(), admin.getSecondName(), admin.getUser(), admin.getPassword(), admin.getEmail());
    }

    public boolean existsAdmin (Admin admin){
        int countClients = jdbcTemplate.queryForObject(EXISTS_ADMIN, Integer.class, admin.getId());
        return countClients > 0;
    }
}
