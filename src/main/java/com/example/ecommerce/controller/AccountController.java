package com.example.ecommerce.controller;

import com.example.ecommerce.domain.users.Account;
import com.example.ecommerce.service.IClientService;
import com.example.ecommerce.service.ISellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AccountController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private IClientService iClientService;
    private ISellerService iSellerService;

    public AccountController(IClientService iClientService, ISellerService iSellerService) {
        this.iClientService = iClientService;
        this.iSellerService = iSellerService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register(){
        ModelAndView model = new ModelAndView("register");
        model.addObject("type", new String[]{"CLIENT","SELLER","ADMIN"});
        model.addObject("account", new Account());
        return model;
    }

    @RequestMapping (value="/register", method = RequestMethod.POST)
    public String processRegister (@ModelAttribute("account") @Valid Account account, Model model, BindingResult result, RedirectAttributes flash, SessionStatus status){
        if (result.hasErrors()) {
            model.addAttribute("title", "Register a new account");
            model.addAttribute("account", account);
            return "register";
        }

        switch(account.getType()) {
            case CLIENT:
                iClientService.insert(account);
                break;
            case SELLER:
                iSellerService.insert(account);
                break;
        }

        status.setComplete();
        flash.addFlashAttribute("success", (account.getId() != 0) ? "Account edited succesfully!" : "Account created succesfully!");
        return "product/products";
    }

    @GetMapping(value = "/listusers")
    public String listUsers(Model model) {
        model.addAttribute("title", "Listed accounts");
        model.addAttribute("clients", iClientService.findAll());
        return "users";
    }
}