package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IlanTesti extends BaseSeleniumTest {
    @Test
    public void yeniIlanOlusturmaTesti() {
        driver.get("http://app:8080/yeni.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Elementlerin varlığını bekle ve temizle (varsa eski veriyi sil)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("baslik"))).clear();
        driver.findElement(By.id("baslik")).sendKeys("Test Baslik");

        driver.findElement(By.id("aciklama")).clear();
        driver.findElement(By.id("aciklama")).sendKeys("Test Aciklama");

        driver.findElement(By.id("kaydet")).click();

        // Mesajın DOM'da görünür olmasını bekle
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mesaj")));
        System.out.println("✅ Stage 5: İlan başarıyla mühürlendi!");
    }
}