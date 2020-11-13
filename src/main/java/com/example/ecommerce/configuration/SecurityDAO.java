package com.example.ecommerce.configuration;

import com.example.ecommerce.domain.users.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SecurityDAO {

    JdbcTemplate jdbcTemplate;
    private final String INSERT_USER = "INSERT INTO users (username, name, secondName, email, password, enabled, role) values (?, ?, ?, ?, ?, ?, ?)";
    private final String UPDATE_USER = "UPDATE users SET name = ?, secondName = ?, email = ? , password = ?, enabled = ?, role = ?";
    private final String INSERT_ROLE = "INSERT INTO authorities (username, role) values(?, ?)";

    public SecurityDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createUserAndRole(Account account) {
        int status = (account.getStatus()) ? 1 : 0;
        String rol = account.getType().toString().toUpperCase();
        jdbcTemplate.update(INSERT_USER, account.getUsername(), account.getName(), account.getSecondName(), account.getEmail(), account.getPassword(), status, rol);
        jdbcTemplate.update(INSERT_ROLE, account.getUsername(), rol);
    }

    public void updateUser (Account account){
        int status = (account.getStatus()) ? 1 : 0;
        String rol = account.getType().toString().toUpperCase();
        jdbcTemplate.update(UPDATE_USER, account.getName(), account.getSecondName(), account.getEmail(), account.getPassword(), status, rol );
    }
}
