package com.example.careermanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticPageController {

    @GetMapping("/giris")
    public String giris() {
        return "forward:/giris.html";
    }

    @GetMapping("/panel")
    public String panel() {
        return "forward:/panel.html";
    }

    @GetMapping("/kariyer/hedef/yeni")
    public String yeniKariyerHedefi() {
        return "forward:/kariyer/hedef/yeni.html";
    }

    @GetMapping("/cikis")
    public String cikis() {
        return "redirect:/giris";
    }

}
