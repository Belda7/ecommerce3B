package com.example.ecommerce.controller;

import com.example.ecommerce.domain.users.Account;
import com.example.ecommerce.service.IClientService;
import com.example.ecommerce.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static com.example.ecommerce.domain.users.AccountType.CLIENT;
import static com.example.ecommerce.domain.users.AccountType.SELLER;


@Controller
public class RegisterController {

    @Autowired
    private IClientService iClientService;

    @Autowired
    private ISellerService iSellerService;

    String[] types = {"CLIENT","SELLER","ADM"};

    @GetMapping(value = "/create")
    public String register(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("type", types);
        return "register";
    }

    @PostMapping(value="/create")
    public ModelAndView create(@Valid Account account, BindingResult result, RedirectAttributes flash, SessionStatus status){
        ModelAndView model = new ModelAndView();
        if (result.hasErrors()) {
            model.addObject("account", account);
            model.addObject("title", "Account Form");
            model.setViewName("redirect : /create");
        }
        String mensajeFlash = (account.getId() != null) ? "Cuenta editada con éxito" : "Cuenta creada con éxito!";

        if (account.getType()==CLIENT) {
            iClientService.insert(account);
        } else if (account.getType()==SELLER) {
            iSellerService.insert(account);
        }

        flash.addFlashAttribute("success", mensajeFlash);
        status.setComplete();
        model.setViewName("redirect:/index");
        return model;
    }

    @GetMapping(value = "/listar")
    public String listar(Model model) {
        model.addAttribute("title", "Listing all the clients");
        model.addAttribute("clients", iClientService.findAll());
        return "listar";
    }

}