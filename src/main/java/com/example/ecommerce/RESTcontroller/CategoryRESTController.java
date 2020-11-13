package com.example.ecommerce.RESTcontroller;

import com.example.ecommerce.domain.Category;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.persistence.CategoryDAO;
import com.example.ecommerce.persistence.ProductDAO;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/")
public class CategoryRESTController {
    private final CategoryDAO categoryDAO;
    private final ProductDAO productDAO;

    private Category oldCategory;

    public CategoryRESTController(CategoryDAO categoryDAO, ProductDAO productDAO) {
        this.categoryDAO = categoryDAO;
        this.productDAO = productDAO;
    }

    @GetMapping("categories")
    public List<Category> getAllCategories() {
        return categoryDAO.findAll();
    }

    @PostMapping("categories/create-category")
    public Category confirmCreateCategroy( @Valid Category category, Errors errors) throws Exception {
        if (errors.hasErrors()) {
            throw new Exception("");
        }

        categoryDAO.insert(category);

        return category;
    }

    @PutMapping("categories/edit-category/{categoryName}")
    public String confirmEditCategory(@Valid Category category, Errors errors) throws Exception {
        if (errors.hasErrors()) {
            throw new Exception("");
        }

        //TODO

        return null;
    }

    @PostMapping("categories/delete-category/{categoryName}")
    public Category confirmDeleteCategory(@PathVariable String categoryName) {
        //Set all the products from the category to category 'Uncategorized'
        List<Product> products = productDAO.Category(categoryName);
        products.forEach(p -> p.setCategory("uncategorized"));
        products.forEach(p -> productDAO.update(p));
        //Remove the category
        Category category = categoryDAO.findCategoryByName(categoryName).get(0);
        categoryDAO.delete(category);
        return category;
    }
}
