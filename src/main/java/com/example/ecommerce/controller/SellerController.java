package com.example.ecommerce.controller;

import com.example.ecommerce.domain.users.Seller;
import com.example.ecommerce.service.ISellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Map;

@Controller("sellerController")
@SessionAttributes("seller")
public class SellerController {

    @Autowired
    private ISellerService iSellerService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public SellerController(ISellerService iSellerService) {
        this.iSellerService = iSellerService;
    }

    @GetMapping("/seller/viewSeller/{idseller}")
    public String view(@PathVariable(value = "idseller") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Seller seller = iSellerService.findOne(id);

        if (seller == null) {
            flash.addFlashAttribute("error", "El ID del vendedor no existe en la BBDD!");
            return "redirect:/listar";
        }
        model.put("seller", seller);
        model.put("title", "Edit Seller");
        log.info("SELECTED SELLER: " + seller.toString());
        return "seller/viewSeller";

    }

    @PostMapping("/seller/formSeller/{idseller}")
    public String edit(@PathVariable(value = "idseller") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Seller seller = iSellerService.findOne(id);

        if (seller == null) {
                flash.addFlashAttribute("error", "El ID del vendedor no existe en la BBDD!");
                return "redirect:/listar";
        }
        model.put("seller", seller);
        model.put("title", "Edit Seller");
        return "seller/formSeller";
    }

    @RequestMapping(value = "/seller/formSeller", method = RequestMethod.POST)
    public String guardar(@Valid Seller seller,
                          BindingResult result,
                          Model model,
                          RedirectAttributes flash,
                          SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("seller", seller);
            model.addAttribute("title", "Seller Form");
            return "redirect:seller/formSeller";
        }

        String mensajeFlash = (seller.getId() != null) ? "Seller saved" : "Seller created";
        iSellerService.update(seller);
        status.setComplete();
        model.addAttribute(seller);
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/";
    }

}
