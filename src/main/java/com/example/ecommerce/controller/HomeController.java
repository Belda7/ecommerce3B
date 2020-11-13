package com.example.ecommerce.controller;

import com.example.ecommerce.persistence.ProductDAO;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private ProductDAO productDAO;

    @GetMapping("/login")
    public String login(@RequestParam(value="error", required = false) String error,
            @RequestParam(value="logout", required = false) String logout,
            Model model, Principal principal, RedirectAttributes flash){
        if (principal != null) {
            flash.addFlashAttribute("info", "Ya tienes iniciada la sesión");
            return "redirect:/";
        }
        if (error != null){
            model.addAttribute("error", "Nombre de usuario o contraseña incorrecto");
        }
        if (logout != null){
            model.addAttribute("success", "Sesión cerrada con éxito");
        }
        return "login";
    }

    @GetMapping({"/index", "", "/"})
    public String homepage(Model model) {

        model.addAttribute("title", "All the products");
        model.addAttribute("products", productDAO.findAll());
        return "product/products";
    }
  
    @GetMapping("/contact-us")
    public String contactUs(Model model) {
        model.addAttribute("title", "Contact us");
        return "contact";
    }
  
    @GetMapping("/weather/{cityname}")
    public String weather(Model model, @PathVariable String cityname) throws JSONException {
        JSONObject mainWeatherInfo = (JSONObject) new JSONObject(new RestTemplate().getForObject(
                "http://api.openweathermap.org/data/2.5/weather?q={name}&appid=e42ddff56d3e7616556e3771ac09a968",
                String.class,
                cityname
        ))
                .get("main");

        model.addAttribute("degrees", Math.round((double) mainWeatherInfo.get("temp") - 273.15));
        model.addAttribute("title", "The current weather in " + cityname);

        return "weather";
    }
}
