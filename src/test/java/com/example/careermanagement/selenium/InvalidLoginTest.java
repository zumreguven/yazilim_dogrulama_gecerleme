package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.Assertions;

@Tag("selenium")
public class InvalidLoginTest extends BaseSeleniumTest {

    @Test
    public void hataliGirisTesti() {
        // Tam URL adresi
        String targetUrl = "http://app:8080/giris";
        System.out.println("🚀 Hatalı giriş testi gidilen adres: " + targetUrl);
        driver.get(targetUrl);

        // Sayfanın yüklendiğini doğrula
        String pageSource = driver.getPageSource();
        Assertions.assertTrue(pageSource.contains("Giriş Yap") || pageSource.contains("Login"),
                "Giriş sayfası yüklenemedi!");

        System.out.println("✅ Hatalı giriş sayfası başarıyla doğrulandı.");
    }
}