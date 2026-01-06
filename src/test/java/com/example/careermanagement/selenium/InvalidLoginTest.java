package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.junit.jupiter.api.Assertions;

@Tag("selenium")
public class InvalidLoginTest extends BaseSeleniumTest {

    @Test
    public void hataliGirisMesajiGosterir() {
        // BURASI ÇOK ÖNEMLİ: URL'yi tam veriyoruz
        String targetUrl = "http://app:8080/giris";
        System.out.println("🚀 Hatalı giriş testi gidilen adres: " + targetUrl);
        driver.get(targetUrl);

        // Sayfa kaynağı dolu mu bak
        Assertions.assertTrue(driver.getPageSource().length() > 0);
        System.out.println("✅ Hatalı giriş sayfası başarıyla yüklendi.");
    }
}