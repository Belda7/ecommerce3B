package com.example.ecommerce.controller;

import com.example.ecommerce.domain.Category;
import com.example.ecommerce.domain.Product;
import com.example.ecommerce.persistence.CategoryDAO;
import com.example.ecommerce.persistence.ProductDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CategoryController {
    private final CategoryDAO categoryDAO;
    private final ProductDAO productDAO;

    private Category oldCategory;

    public CategoryController(CategoryDAO categoryDAO, ProductDAO productDAO) {
        this.categoryDAO = categoryDAO;
        this.productDAO = productDAO;
    }

    @GetMapping("categories")
    public String getAllCategories(Model model) {
        model.addAttribute("title", "All categories");
        model.addAttribute("categories", categoryDAO.findAll());
        return "category/categories";
    }

    @GetMapping("categories/create-category")
    public String createCategory(Model model) {
        model.addAttribute("title", "Create a new category");
        model.addAttribute("newCategory", new Category());
        model.addAttribute("creation", true);
        return "category/create-category";
    }

    @PostMapping("categories/create-category")
    public String confirmCreateCategroy(Model model, @Valid Category category, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("title" ,"Creating a new category");
            model.addAttribute("newCategory", category);
            model.addAttribute("creation", true);
            return "category/create-category";
        }

        categoryDAO.insert(category);

        redirectAttributes.addAttribute("categoryName", category.getNameCategory());
        return "redirect:/products/category/{categoryName}";
    }

    @GetMapping("categories/edit-category/{categoryName}")
    public String editCategory(Model model, @PathVariable String categoryName) {
        model.addAttribute("title", "Editing category " + categoryName);
        model.addAttribute("newCategory", categoryDAO.findCategoryByName(categoryName).get(0));
        model.addAttribute("creation", false);
        oldCategory = categoryDAO.findCategoryByName(categoryName).get(0);
        return "category/create-category";
    }

    @PostMapping("categories/edit-category/{categoryName}")
    public String confirmEditCategory(Model model, @Valid Category category, Errors errors, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Editing category " + category.getNameCategory());
            model.addAttribute("newCategory", category);
            model.addAttribute("creation", false);
            return "category/create-category";
        }

        //Set all the products from the category to category 'Uncategorized'
        List<Product> products = productDAO.Category(oldCategory.getNameCategory());
        products.forEach(p -> p.setCategory("uncategorized"));
        products.forEach(p -> productDAO.update(p));
        //update the category by removing old one and adding new one
        categoryDAO.delete(oldCategory);
        categoryDAO.insert(category);
        //Set all the products from the category to the new categoryname
        products.forEach(p -> p.setCategory(category.getNameCategory()));
        products.forEach(p -> productDAO.update(p));

        redirectAttributes.addAttribute("categoryName", category.getNameCategory());
        return "redirect:/products/category/{categoryName}";
    }

    @GetMapping("categories/delete-category/{categoryName}")
    public String deleteCategory(Model model, @PathVariable String categoryName) {
        model.addAttribute("title", "Deleting category " + categoryName);
        Category category = categoryDAO.findCategoryByName(categoryName).get(0);
        model.addAttribute("identifier", category.getNameCategory());
        return "delete";
    }

    @PostMapping("categories/delete-category/{categoryName}")
    public String confirmDeleteCategory(@PathVariable String categoryName) {
        //Set all the products from the category to category 'Uncategorized'
        List<Product> products = productDAO.Category(categoryName);
        products.forEach(p -> p.setCategory("uncategorized"));
        products.forEach(p -> productDAO.update(p));
        //Remove the category
        categoryDAO.delete(categoryDAO.findCategoryByName(categoryName).get(0));
        return "redirect:/categories/";
    }
}
