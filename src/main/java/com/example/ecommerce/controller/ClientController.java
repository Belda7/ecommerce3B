package com.example.ecommerce.controller;

import com.example.ecommerce.domain.users.Client;
import com.example.ecommerce.service.IClientService;
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
import java.util.List;
import java.util.Map;

@Controller("clientController")
@SessionAttributes("client")
public class ClientController {

    @Autowired
    private IClientService iClientService;

    private final Logger log = LoggerFactory.getLogger(getClass());

    public ClientController(IClientService iClientService) {
        this.iClientService = iClientService;
    }

    @GetMapping("/client/viewClient/{idclient}")
    public String view(@PathVariable(value = "idclient") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Client client = iClientService.findOne(id);

        if (client == null) {
            flash.addFlashAttribute("error", "The client ID doesn't exist!");
            return "redirect:/listar";
        }
        model.put("client", client);
        model.put("title", "Edit Client");
        log.info("SELECTED CLIENT: " + client.toString());
        return "client/viewClient";
    }

    @PostMapping("/client/formClient/{idclient}")
    public String edit(@PathVariable(value = "idclient") Long id, Map<String, Object> model, RedirectAttributes flash) {
        Client client = iClientService.findOne(id);

        if (client == null) {
            flash.addFlashAttribute("error", "The id of the client doesn't exist!");
            return "redirect:/listar";
        }
        model.put("client", client);
        model.put("title", "Save Client");
        return "client/formClient";
    }

    @RequestMapping(value = "/client/formClient", method = RequestMethod.POST)
    public String guardar(@Valid Client client,
                          @RequestParam (name="address_[]", required = false) String[] address,
                          @RequestParam (name="card_[]", required = false) String[] card,
                          BindingResult result,
                          Model model,
                          RedirectAttributes flash,
                          SessionStatus status) {


        if (result.hasErrors()) {
            model.addAttribute("client", client);
            model.addAttribute("title", "Client Form");
            return "redirect:client/formClient";
        }

        if (address != null){
            for(int i=0; i< address.length; i++){
                iClientService.addAddress(client, address[i]);
                log.info("Address: " + address[i] + "added to " + client.getUser() + " address list");
            }
        }

        String mensajeFlash = (client.getId() != null) ? "Cliente guardado" : "Cliente creado";
        iClientService.update(client);
        status.setComplete();
        model.addAttribute(client);
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/";
    }

    @GetMapping(value="/add-address/{idclient}", produces={"application/json"})
    public @ResponseBody List<String> updateAddress(@PathVariable Long idclient, @RequestParam String address){
        Client client = iClientService.findOne(idclient);
        iClientService.addAddress(client, address);
        return iClientService.findOne(idclient).getAddress();
    }
}
