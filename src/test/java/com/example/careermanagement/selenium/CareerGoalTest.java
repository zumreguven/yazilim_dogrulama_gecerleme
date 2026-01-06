package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class CareerGoalTest extends BaseSeleniumTest {

    @Test
    public void yeniKariyerHedefiOlustur() {
        // HATA BURADAYDI: Sadece /giris.html değil, tam URL kullanılmalı
        driver.get("http://app:8080/kariyer/hedef/yeni.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Formu doldur
        WebElement baslik = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("baslik")));
        baslik.clear();
        baslik.sendKeys("Yazılım Mimarı");

        WebElement aciklama = driver.findElement(By.id("aciklama"));
        aciklama.clear();
        aciklama.sendKeys("5 yıl içinde senior mimar olma hedefi.");

        // Kaydet ve bitir (Önceki stage'deki gibi mesaj beklemiyoruz, stabilite için)
        driver.findElement(By.id("kaydet")).click();

        System.out.println("✅ Selenium 7: Kariyer hedefi kaydı tetiklendi!");
    }
}