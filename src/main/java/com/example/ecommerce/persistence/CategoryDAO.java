package com.example.ecommerce.persistence;

import com.example.ecommerce.domain.Category;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("categoryDAO")
public class CategoryDAO {

    private final JdbcTemplate jdbcTemplate;


    private final String INSERT_CATEGORY ="INSERT INTO category (categoryname) VALUES (?)";
    private final String FIND_ALL = "SELECT * FROM category";
    private final String UPDATE_CATEGORY = "UPDATE category set categoryname = ?";
    private final String EXISTS_CATEGORY = "select * from category where categoryname = ?";
    private final String FIND_BY_ID = EXISTS_CATEGORY;
    private final String DELETE_CATEGORY = "DELETE FROM category where categoryname = ?";

    private RowMapper<Category> mapper = (resultSet, i) -> {
        Category category = new Category.CategoryBuilder()
                .nameCategory(resultSet.getString ("categoryname"))
                .image(resultSet.getString("image"))
                .build();
        return category;
    };

    public CategoryDAO (JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Category> findAll (){
        return jdbcTemplate.query (FIND_ALL, mapper);
    }

    public int insert (Category category){
        return jdbcTemplate.update(INSERT_CATEGORY, category.getNameCategory());
    }

    public int update (Category category){
        return jdbcTemplate.update(UPDATE_CATEGORY, category.getNameCategory());
    }

    public boolean existsCategory (String name){
        return jdbcTemplate.query(EXISTS_CATEGORY, mapper, name).size() > 0;
    }

    public List<Category> findCategoryByName(String categoryName) {
        return jdbcTemplate.query(FIND_BY_ID, mapper, categoryName);
    }

    public int delete(Category category) { return jdbcTemplate.update(DELETE_CATEGORY, category.getNameCategory());}
}