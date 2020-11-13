package com.example.ecommerce.persistence.users;

import com.example.ecommerce.configuration.SecurityDAO;
import com.example.ecommerce.domain.users.Account;
import com.example.ecommerce.domain.users.Client;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository("clientDAO")
public class ClientDAO {

    private JdbcTemplate jdbcTemplate;
    private SecurityDAO securityDAO;

    public ClientDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String INSERT_CLIENT="INSERT INTO client (idclient, username, password, name, secondName, email, directions, phonenumber, image, status, tipo  ) values (?,?,?,?,?,?,?,?,?,?,?)";
    private final String FIND_ALL="SELECT * FROM client";
    private final String FIND_ACTIVE_CLIENTS = "SELECT * FROM client WHERE status = 1";
    private final String FIND_BY_USERNAME="SELECT * FROM client WHERE username = ?";
    private final String FIND_BY_ID ="SELECT * FROM client WHERE idclient = ?";
    private final String UPDATE_CLIENT="UPDATE CLIENT SET name = ?, secondName = ?, email = ?, image = ?, status = ?, tipo = ? where idclient = ?";
    private final String EXISTS_CLIENT="select count(*) from client where idclient = ?";

    private final String LAST_ID = "select max(idclient) from client";

    private final RowMapper<Client> mapper = (resultSet, i) -> {
        Client client = new Client();
            client.setId(resultSet.getLong("idclient"));
            client.setUser(resultSet.getString("username"));
            client.setPassword(resultSet.getString("password"));
            client.setName(resultSet.getString("name"));
            client.setSecondName(resultSet.getString("secondName"));
            client.setEmail(resultSet.getString("email"));
            client.setAddress(resultSet.getString("directions"));
            client.setPhoneNumber(resultSet.getString("phonenumber"));
            client.setImg(resultSet.getString("image"));
            client.setStatus(resultSet.getString("status").equals('1')? true : false);
            client.setType(resultSet.getString("tipo"));
        return client;
    };

    public List<Client> findAll() {
        return jdbcTemplate.query(FIND_ALL, mapper);
    }

    public List<Client> findActiveClients() {
        return jdbcTemplate.query(FIND_ACTIVE_CLIENTS, mapper);
    }

    public Client findByUsername(String username){
        return jdbcTemplate.queryForObject(FIND_BY_USERNAME, new Object[]{username} ,mapper);
    }

    public Client findById (Long id) {

        return jdbcTemplate.queryForObject(FIND_BY_ID, new Object[]{id}, mapper);
    }

    public int insert (Account account){
        Client newClient = new Client(account);
        newClient.setId(lastID());
        String status = newClient.getStatus() ? "1" : "0";
        return jdbcTemplate.update(INSERT_CLIENT, new Object[] {newClient.getId(), newClient.getUser(), newClient.getPassword(), newClient.getName(), newClient.getSecondName(), newClient.getEmail(), newClient.toStringAddress(), newClient.getPhoneNumber(), newClient.getImg(), status, newClient.getType().tipusCompte});
    }

    public int update (Client client){
        String status = client.getStatus() ? "1" : "0";
        securityDAO.updateUser((Account) client);
        return jdbcTemplate.update(UPDATE_CLIENT, client.getName(), client.getSecondName(), client.getEmail(), client.getImg(), status, client.getType().tipusCompte, client.getId());
    }

    public boolean existsClient (Client client){
        return jdbcTemplate.queryForObject(EXISTS_CLIENT, Long.class, client.getId()) > 0;
    }

    public void delete (Client client){
        client.changeStatus(false);
    }

/*    public boolean login(Client cli) {
        Client client = this.findByUsername(cli.getUser());
        return cli.equals(client);
    }
*/
    private Long lastID (){
        Long id = jdbcTemplate.queryForObject(LAST_ID, Long.class);
        return id+1;
    }

}