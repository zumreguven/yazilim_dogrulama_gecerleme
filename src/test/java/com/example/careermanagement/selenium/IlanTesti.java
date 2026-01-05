package com.example.careermanagement.selenium;

import org.openqa.selenium.By;
// ...existing code...
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IlanTesti extends BaseSeleniumTest {

    @Test
    public void yeniIlanOlusturmaTesti() {
        // 1. Önce giriş yap
        girisYap();

        // 2. İlan oluştur sayfasına git
        driver.get(BASE_URL + "/ilan/yeni");

        // 3. Formu doldur
        assertThat(driver.findElements(By.id("baslik")).size()).isGreaterThan(0);
        driver.findElement(By.id("baslik")).sendKeys("Yazılım Uzmanı");
        driver.findElement(By.id("aciklama")).sendKeys("Aranıyor yazılım uzmanı");
        driver.findElement(By.id("kaydet")).click();

        // 4. Başarı mesajını kontrol et (alert-success görünmeli)
        assertThat(driver.findElements(By.cssSelector(".alert-success")).size()).isGreaterThan(0);

    }

    private void girisYap() {
        // Use the test helper to mark the browser as authenticated
        authenticateAs("admin");
    }
}