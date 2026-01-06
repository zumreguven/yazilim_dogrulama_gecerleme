package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IlanTesti extends BaseSeleniumTest {
    @Test
    public void yeniIlanOlusturmaTesti() {
        // GÜNCELLEME: SecurityYapilandirma'da izin verdiğin adrese gidiyoruz
        driver.get("http://app:8080/kariyer/hedef/yeni.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // HTML ID'lerin ile etkileşim (Dizin yapın değişse de ID'lerin aynı kalmalı)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("baslik"))).sendKeys("Test Kariyer Hedefi");
        driver.findElement(By.id("aciklama")).sendKeys("Security izinleri kontrol edildi.");
        driver.findElement(By.id("kaydet")).click();

        // Mesajın görünmesini bekle
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mesaj")));
        System.out.println("✅ Selenium 5: İzin verilen yol üzerinden ilan başarıyla oluşturuldu!");
    }
}