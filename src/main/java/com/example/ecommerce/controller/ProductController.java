package com.example.ecommerce.controller;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.persistence.CategoryDAO;
import com.example.ecommerce.persistence.CompanyDAO;
import com.example.ecommerce.persistence.ProductDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
public class ProductController {

    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final CompanyDAO companyDAO;

    private enum filterType {product, category, company, description};

    public ProductController(ProductDAO productDAO, CategoryDAO categoryDAO, CompanyDAO companyDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.companyDAO = companyDAO;
    }

    @GetMapping("products/searchByWord/{word}")
    public String getWordsByString(@PathVariable String word, Model model) {
        //Lowercase to make searches easier
        word = word.toLowerCase();

        //Creating the list of products that will be returned
        List<Product> products = new ArrayList<>();

        //Applying the search filter and avoid doubles in the process
        addToList(products, productDAO.Category(word));
        addToList(products, productDAO.Company(word));
        addToList(products, productDAO.Name(word));
        for (filterType value : filterType.values()) {
            addToListContains(products, value, word);
        }

        model.addAttribute("title", "Reviewing products related to the word " + word);
        model.addAttribute("products", products);

        return "product/products";
    }

    public void addToList(List<Product> list, List<Product> toAdd) {
        toAdd.forEach(li -> {
            if (!list.contains(li)) {
                list.add(li);
            }
        });
    }

    public List<Product> addToListContains(List<Product> list, filterType type, String word) {
        Predicate<Product> filter = null;
        switch(type) {
            case company: filter = p -> p.getName().toLowerCase().contains(word);
                break;
            case product: filter = p -> p.getCategory().toLowerCase().contains(word);
                break;
            case category: filter = p -> p.getCompany().toLowerCase().contains(word);
                break;
            case description: filter = p -> p.getDescription().toLowerCase().contains(word);
            break;
        }
        addToList(list, productDAO.findAll().stream().filter(filter).collect(Collectors.toList()));
        return list;
    }


    @GetMapping("products")
    public String products(Model model) {
        model.addAttribute("title", "All the products");
        model.addAttribute("products", productDAO.findAll());
        return "product/products";
    }

    @GetMapping("products/{id}")
    public String product(Model model, @PathVariable int id) {
        Product product = productDAO.findById(id).get(0);
        model.addAttribute("title", "Product: " + product.getName());
        model.addAttribute("product", product);
        return "product/product";
    }

    @GetMapping("products/create-product")
    public String createProduct(Model model) {
        model.addAttribute("title", "Creating a new product");
        model.addAttribute("newProduct", new Product());
        model.addAttribute("categories",  categoryDAO.findAll());
        model.addAttribute("companies", companyDAO.findAll());
        model.addAttribute("creation", true);
        return "product/create-product";
    }

    @PostMapping("products/create-product")
    public String createProduct(@Valid Product product, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Creating a new product");
            model.addAttribute("newProduct", product);
            model.addAttribute("creation", true);
            return "product/create-product";
        }

        //Insert product
        productDAO.insert(product);

        redirectAttributes.addAttribute("id", product.getIdProduct());
        return "redirect:/products/{id}";
    }

    @GetMapping("products/edit-product/{id}")
    public String editProduct(Model model, @PathVariable int id) {
        Product product = productDAO.findById(id).get(0);
        model.addAttribute("title", "Editing product " + product.getName());
        model.addAttribute("newProduct", product);
        model.addAttribute("categories",  categoryDAO.findAll());
        model.addAttribute("companies", companyDAO.findAll());
        model.addAttribute("creation", false);
        return "product/create-product";
    }

    @PostMapping("products/edit-product/{id}")
    public String editProduct(@Valid Product product, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Editing product " + product.getName());
            model.addAttribute("newProduct", product);
            model.addAttribute("categories",  categoryDAO.findAll());
            model.addAttribute("companies", companyDAO.findAll());
            model.addAttribute("creation", false);
            return "product/create-product";
        }

        //Update product
        productDAO.update(product);

        redirectAttributes.addAttribute("id", product.getIdProduct());
        return "redirect:/products/{id}";
    }

    @GetMapping("products/delete-product/{id}")
    public String deleteProduct(@PathVariable int id, Model model) {
        Product product = productDAO.findById(id).get(0);
        model.addAttribute("title", "Deleting product " + product.getName());
        model.addAttribute("identifier", product.getName() + " (With id " +  product.getIdProduct() + " )");
        return "delete";
    }

    @PostMapping("products/delete-product/{id}")
    public String deleteProduct(@PathVariable int id) {
        productDAO.delete(productDAO.findById(id).get(0));
        return "redirect:/products";
    }

    @GetMapping("/products/company/{companyName}/")
    public String getProductsFromCompany(@PathVariable String companyName, Model model) {
        model.addAttribute("title", "Viewing products from company " +  companyName);
        model.addAttribute("products", productDAO.findAll().stream().filter(p -> p.getCompany().equalsIgnoreCase(companyName)).collect(Collectors.toList()));
        return "product/products";
    }

    @GetMapping("/products/category/{categoryName}")
    public String getProductsFromCategory(@PathVariable String categoryName, Model model) {
        model.addAttribute("title", "Viewing products from category " +  categoryName);
        model.addAttribute("products", productDAO.findAll().stream().filter(p -> p.getCategory().equalsIgnoreCase(categoryName)).collect(Collectors.toList()));
        return "product/products";
    }
}
