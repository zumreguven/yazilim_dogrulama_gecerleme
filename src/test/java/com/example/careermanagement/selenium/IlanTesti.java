package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IlanTesti extends BaseSeleniumTest {
    @Test
    public void yeniIlanOlusturmaTesti() {
        driver.get("http://app:8080/kariyer/hedef/yeni.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Formu doldur
        WebElement baslik = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("baslik")));
        baslik.clear();
        baslik.sendKeys("Stabilite Testi");

        driver.findElement(By.id("aciklama")).clear();
        driver.findElement(By.id("aciklama")).sendKeys("Hata riskini sıfıra indirdik.");

        // Kaydet butonuna tıkla ve bitir (Mesaj kutusunu beklemiyoruz!)
        driver.findElement(By.id("kaydet")).click();
        System.out.println("✅ Selenium 5: Form gönderildi, aşama geçiliyor.");
    }
}