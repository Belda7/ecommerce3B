package com.example.ecommerce.persistence;

import com.example.ecommerce.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("productDAO")
public class ProductDAO {


    private final JdbcTemplate jdbcTemplate;

    public ProductDAO (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //Orden Visualizacion de productos
    private final String OrderBy="SELECT * FROM PRODUCT ORDER BY ? ?"; //(name-price-review_grade)(ASC-DESC)
    //Filtros Busqueda de productos
    private final String Price="SELECT * FROM PRODUCT WHERE price BETWEEN ? AND ?";
    private final String Review="SELECT * FROM PRODUCT WHERE REVIEW_GRADE BETWEEN ? AND ?";
    private final String Category="SELECT * FROM PRODUCT WHERE CATEGORY_CATEGORYNAME = ?";
    private final String Company="SELECT * FROM PRODUCT WHERE COMPANY_COMPANYNAME = ?";
    private final String Name="SELECT * FROM PRODUCT WHERE NAME = ?";

    public List<Product> OrderBy(String orderBy, boolean how){        return jdbcTemplate.query(OrderBy,new Object[]{orderBy,isASC(how)},mapper);}
    public List<Product> Price (int low, int high) { return jdbcTemplate.query(Price, new Object[]{low,high}, mapper); }
    public List<Product> Review (int low, int high) {        return jdbcTemplate.query(Review, new Object[]{low,high}, mapper);    }
    public List<Product> Category (String name) {
        return jdbcTemplate.query(Category, new Object[]{name}, mapper);
    }
    public List<Product> Company (String name) {        return jdbcTemplate.query(Company, new Object[]{name}, mapper);    }
    public List<Product> Name (String name) {        return jdbcTemplate.query(Name, new Object[]{name}, mapper);}


    private final String FIND_ALL="SELECT * FROM PRODUCT";
    private final String INSERT_PRODUCT = "INSERT INTO product (idproduct, name, description, price, stock, company_companyname, promotion_idpromotion, category_categoryname, visibility) values (?,?,?,?,?,?,?,?,?)";
    private final String FIND_BY_ID = "select * from product where idproduct = ?";
    private final String DELETE_PRODUCT = "DELETE FROM product where idproduct = ?";
    private final String UPDATE_PRODUCT = "UPDATE product SET name = ?, description = ?, price = ?, stock = ?, company_companyname = ?, promotion_idpromotion = ? , category_categoryname = ?, visibility = ? WHERE idproduct = ? ";
    private final String EXISTS_PRODUCT = "SELECT COUNT(*) FROM product where idproduct = ?";

    public static RowMapper<Product> mapper = (resultSet, i) -> {
        Product product = new Product.ProductBuilder()
                .idProduct(resultSet.getInt("idProduct"))
                .name(resultSet.getString("name"))
                .description(resultSet.getString("description"))
                .price(resultSet.getDouble("price"))
                .stock(resultSet.getInt("stock"))
                .company(resultSet.getString("company_companyName"))
                .promotion(resultSet.getInt("promotion_idpromotion"))
                .category(resultSet.getString("category_categoryname"))
                .visibility(resultSet.getBoolean("visibility"))
                .image(resultSet.getString("image"))
                .build();
        return product;
    };

    public String isASC(boolean asc){
        if(asc) return "ASC";
        return "DESC";
    }

    //Cuando llames a algun OrderBy$XXX teneis que poner como se ordena(name-price-review_grade) y true=ASC false=DESC
    //ORDER BY$FILTRO1
    //PRICE

    public List<Product> OrderBy$Price (int low, int high,String orderBy, boolean how) {
        return jdbcTemplate.query(OrderBy$Price, new Object[]{low,high,orderBy,isASC(how)}, mapper);
    }

    //REVIEW
    public List<Product> OrderBy$Review (int low, int high,String orderBy, boolean how) {
        return jdbcTemplate.query(OrderBy$Review, new Object[]{low,high,orderBy,isASC(how)}, mapper);
    }

    //NAME
    public List<Product> OrderBy$Name (String name,String orderBy, boolean how) {
        return jdbcTemplate.query(OrderBy$Name, new Object[]{name,orderBy,isASC(how)}, mapper);
    }

    //CATEGORY
    public List<Product> OrderBy$Category (String name,String orderBy, boolean how) {
        return jdbcTemplate.query(OrderBy$Category, new Object[]{name,orderBy,isASC(how)}, mapper);
    }

    //COMPANY
    public List<Product> OrderBy$Company (String name,String orderBy, boolean how) {
        return jdbcTemplate.query(OrderBy$Company, new Object[]{name,orderBy,isASC(how)}, mapper);
    }

    //ORDER BY&PRICE$REVIEW$NAME$CATEGORY$COMPANY
    public List<Product> OrderBy$Price$Review$Name$Category$Company(int lowP, int highP, int lowR, int highR, String name, String Caname, String Coname, String orderBy, boolean how) {
        return jdbcTemplate.query(OrderBy$Price$Review$Name$Category$Company, new Object[]{lowP, highP, lowR, highR, name, Caname, Coname, orderBy, isASC(how)}, mapper);
    }

    public List<Product> findAll() {
        return jdbcTemplate.query (FIND_ALL, mapper);
    }

    public List<Product> findById(Integer id) {
        return jdbcTemplate.query(FIND_BY_ID, new Object[]{id}, mapper);
    }

    public int insert (Product product){
        return jdbcTemplate.update(INSERT_PRODUCT, product.getIdProduct(), product.getName(), product.getDescription(), product.getPrice(), product.getStock(), product.getCompany(), product.getPromotion(), product.getCategory(), product.isVisible());
    }

    public int update (Product product){
        return jdbcTemplate.update(UPDATE_PRODUCT, product.getName(), product.getDescription(), product.getPrice(), product.getStock(), product.getCompany(), product.getPromotion(), product.getCategory(), product.isVisible(), product.getIdProduct());
    }

    public int delete(Product product) { return jdbcTemplate.update(DELETE_PRODUCT, product.getIdProduct());}

    public boolean existsProduct (Product product){
        int countProduct = jdbcTemplate.queryForObject(EXISTS_PRODUCT, Integer.class, product.getIdProduct());
        return countProduct > 0;
    }

    //ORDER BY$FILTRO1
    //PRICE
    private final String OrderBy$Price="SELECT * FROM PRODUCT WHERE price BETWEEN ? AND ? ORDER BY ? ?";
    //REVIEW
    private final String OrderBy$Review="SELECT * FROM PRODUCT WHERE REVIEW_GRADE BETWEEN ? AND ? ORDER BY ? ?";
    //NAME
    private final String OrderBy$Name="SELECT * FROM PRODUCT WHERE name like '%?%'  ORDER BY ? ? ";
    //CATEGORY
    private final String OrderBy$Category="SELECT * FROM PRODUCT WHERE CATEGORY_CATEGORYNAME = '?' ORDER BY ? ?";
    //COMPANY
    private final String OrderBy$Company="SELECT * FROM PRODUCT WHERE COMPANY_COMPANYNAME = '?' ORDER BY ? ?";
    private final String OrderBy$Price$Review$Name$Category$Company = "SELECT * FROM PRODUCT WHERE (price BETWEEN ? AND ?) AND (REVIEW_GRADE BETWEEN ? AND ?) AND (name like '%pro%') AND (CATEGORY_CATEGORYNAME = '?') AND (COMPANY_COMPANYNAME = '?') ORDER BY ? ?";
}
