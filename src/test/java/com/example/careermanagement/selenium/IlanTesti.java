package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IlanTesti extends BaseSeleniumTest {
    @Test
    public void yeniIlanOlusturmaTesti() {
        // Dosya ismini yeni.html olarak düzelttik
        driver.get("http://app:8080/yeni.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Senin HTML formundaki ID'ler: baslik, aciklama, kaydet
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("baslik"))).sendKeys("Yazılım Mühendisi");
        driver.findElement(By.id("aciklama")).sendKeys("Java ve Selenium bilen takım arkadaşı arıyoruz.");

        // Kaydet butonuna tıkla
        driver.findElement(By.id("kaydet")).click();

        // Senin script'indeki 'mesaj' ID'li div'in görünür olmasını bekle
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mesaj")));

        System.out.println("✅ Selenium 5: Yeni ilan başarıyla oluşturuldu!");
    }
}