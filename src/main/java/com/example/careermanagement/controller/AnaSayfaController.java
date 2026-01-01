package com.example.careermanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AnaSayfaController {

    @GetMapping("/")
    @ResponseBody
    public String anaSayfa() {
        return "Kariyer Yönetim Sistemi API'sine hoş geldiniz!<br>" +
                "API Dokümantasyonu için: <a href='/swagger-ui.html'>/swagger-ui.html</a><br>" +
                "API Tanımları: <a href='/v3/api-docs'>/v3/api-docs</a>";
    }
}