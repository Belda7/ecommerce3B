package com.example.ecommerce.persistence;

import com.example.ecommerce.domain.Card;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("cardDAO")
public class CardDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final String INSERT_CARD = "INSERT INTO card (cardnumber, owner, expiredate, client_idCliente) VALUES (?,?,?,?)";
    private final String FIND_ALL = "select * from card";
    private final String FIND_BY_CLIENTID = "SELECT * FROM CARD WHERE client_idCliente = ?";
    private final String FIND_BY_CARDNUMBER = "SELECT * FROM CARD WHERE cardnumber = ?";
    private final String UPDATE_CARD = "UPDATE CARD SET cardnumber = ?, owner = ?, expiredate = ?, cient_idCliente =?";
    private final String EXISTS_CARD = "SELECT (*) FROM card where client_idCliente = ?";

    private RowMapper<Card> mapper = (resultSet, i) -> {
        Card card = new Card.CardBuilder()
                .cardNumber(resultSet.getString("cardnumber"))
                .owner(resultSet.getString("owner"))
                .expireDate(resultSet.getDate("expiredate"))
                .idClient(resultSet.getInt("client_idCliente"))
                .build();
        return card;
    };

    public CardDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Card> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper);
    }

    public List<Card> findByClientID (Integer idCliente){
        return jdbcTemplate.query(FIND_BY_CLIENTID, new Object[]{idCliente}, mapper);
    }

    public List<Card> findByCardNumber (String cardNumber) {
        return jdbcTemplate.query(FIND_BY_CARDNUMBER, new Object[]{cardNumber}, mapper);
    }

    public int insert (Card card){
        return jdbcTemplate.update(INSERT_CARD, card.getCardNumber(), card.getOwner(), card.getExpireDate(), card.getIdClient());
    }

    public int update (Card card){
        return jdbcTemplate.update(UPDATE_CARD, card.getCardNumber(), card.getOwner(), card.getExpireDate(), card.getIdClient() );
    }

    public boolean existsCard (Card card){
        int countCards = jdbcTemplate.queryForObject(EXISTS_CARD, Integer.class, card.getIdClient());
        return countCards >0;
    }

}
