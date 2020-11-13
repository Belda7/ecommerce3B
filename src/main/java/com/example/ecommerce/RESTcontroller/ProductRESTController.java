package com.example.ecommerce.RESTcontroller;

import com.example.ecommerce.domain.Product;
import com.example.ecommerce.persistence.CategoryDAO;
import com.example.ecommerce.persistence.CompanyDAO;
import com.example.ecommerce.persistence.ProductDAO;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/")
public class ProductRESTController {
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    private final CompanyDAO companyDAO;

    private enum filterType {product, category, company, description};

    public ProductRESTController(ProductDAO productDAO, CategoryDAO categoryDAO, CompanyDAO companyDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
        this.companyDAO = companyDAO;
    }

    @GetMapping(value ="products/searchByWord/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getWordsByString(@PathVariable String word, Model model) {
        //Lowercase to make searches easier
        word = word.toLowerCase();

        //Creating the list of products that will be returned
        List<Product> products = new ArrayList<>();

        //Applying the search filter and avoid doubles in the process
        addToList(products, productDAO.Category(word));
        addToList(products, productDAO.Company(word));
        addToList(products, productDAO.Name(word));
        for (ProductRESTController.filterType value : ProductRESTController.filterType.values()) {
            addToListContains(products, value, word);
        }

        return products;
    }

    public void addToList(List<Product> list, List<Product> toAdd) {
        toAdd.forEach(li -> {
            if (!list.contains(li)) {
                list.add(li);
            }
        });
    }

    public List<Product> addToListContains(List<Product> list, ProductRESTController.filterType type, String word) {
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

    @GetMapping(value ="products", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> products() {
        return productDAO.findAll();
    }

    @GetMapping(value ="products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product product(@PathVariable int id) {
        return productDAO.findById(id).get(0);
    }

    @PostMapping(value ="products/create-product", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product createProduct(@RequestBody @Valid Product product, Errors errors, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (errors.hasErrors()) {
            throw new Exception("");
        }

        //Insert product
        productDAO.insert(product);

        return product;
    }

    @PutMapping(value ="products/edit-product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product editProduct(@RequestBody @Valid Product product, Errors errors, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if (errors.hasErrors()) {
            throw new Exception("");
        }

        //Update product
        productDAO.update(product);

        return product;
    }

    @DeleteMapping(value ="products/delete-product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Product deleteProduct(@PathVariable int id) {
        Product product = productDAO.findById(id).get(0);
        productDAO.delete(product);
        return product;
    }

    @GetMapping(value ="/products/company/{companyName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProductsFromCompany(@PathVariable String companyName) {
        return productDAO.findAll().stream().filter(p -> p.getCompany().equalsIgnoreCase(companyName)).collect(Collectors.toList());
    }

    @GetMapping(value ="/products/category/{categoryName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProductsFromCategory(@PathVariable String categoryName) {
        return productDAO.findAll().stream().filter(p -> p.getCategory().equalsIgnoreCase(categoryName)).collect(Collectors.toList());
    }
}
