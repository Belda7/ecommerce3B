package com.example.ecommerce.controller;

import com.example.ecommerce.domain.Company;
import com.example.ecommerce.persistence.CompanyDAO;
import com.example.ecommerce.persistence.ProductDAO;
import com.example.ecommerce.persistence.users.SellerDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class CompanyController {

    private final CompanyDAO companyDAO;
    private final SellerDAO sellerDAO;
    private final ProductDAO productDAO;

    public CompanyController(CompanyDAO companyDAO, SellerDAO sellerDAO, ProductDAO productDAO) {
        this.companyDAO = companyDAO;
        this.sellerDAO = sellerDAO;
        this.productDAO = productDAO;
    }

    @GetMapping("companies")
    public String getOverviewAllCompanies(Model model) {
        model.addAttribute("title", "Overview of all the companies");
        model.addAttribute("companies", companyDAO.findAll());
        return "company/companies";
    }

    @GetMapping("companies/company/{companyId}")
    public String getOverviewOneCompany(@PathVariable String companyId, Model model) {
        Company company = companyDAO.findByCompanyId(companyId).get(0);
        model.addAttribute("title", "Overview of the company " + company.getName());
        model.addAttribute("company", company);
        model.addAttribute("products", companyDAO.getAllProductsFromCompany(companyId));
        return "company/company";
    }

    @GetMapping("companies/create-company")
    public String getCreateCompany(Model model) {
        model.addAttribute("title", "Creating a new company");
        model.addAttribute("company", new Company());
        model.addAttribute("sellers", sellerDAO.findAll());
        model.addAttribute("creation", true);
        return "company/create-company";
    }

    @PostMapping("companies/create-company")
    public String postCreateCompany(@Valid Company company, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Creating a new company");
            model.addAttribute("company", new Company());
            model.addAttribute("creation", true);
            return "company/create-company";
        }

        companyDAO.insert(company);

        redirectAttributes.addAttribute("id", company.getName());
        return "redirect:/companies/company/{id}";
    }


    @GetMapping("companies/edit-company/{id}")
    public String getEditCompany(Model model, @PathVariable String id) {
        Company c = companyDAO.findByCompanyId(id).get(0);
        model.addAttribute("title", "Editing company " + c.getName());
        model.addAttribute("company", c);
        model.addAttribute("sellers", sellerDAO.findAll());
        model.addAttribute("creation", false);
        return "company/create-company";
    }

    @PostMapping("companies/edit-company/{id}")
    public String postEditCompany(@Valid Company company, Errors errors, Model model, RedirectAttributes redirectAttributes) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Creating a new company");
            model.addAttribute("company", company);
            model.addAttribute("sellers", sellerDAO.findAll());
            model.addAttribute("creation", false);
            return "company/create-company";
        }

        companyDAO.update(company);

        redirectAttributes.addAttribute("id", company.getName());
        return "redirect:/companies/company/{id}";
    }

    @GetMapping("companies/delete-company/{id}")
    public String deleteCompany(Model model, @PathVariable String id) {
        Company c = companyDAO.findByCompanyId(id).get(0);
        model.addAttribute("title", "Deleting company " + c.getName());
        model.addAttribute("identifier", c.getName());
        return "delete";
    }

    @PostMapping("companies/delete-company/{id}")
    public String postEditCompany(@PathVariable String id, Model model) {
        companyDAO.getAllProductsFromCompany(id).forEach(p -> productDAO.delete(p));
        companyDAO.delete(companyDAO.findByCompanyId(id).get(0));
        return "redirect:/companies";
    }
}
