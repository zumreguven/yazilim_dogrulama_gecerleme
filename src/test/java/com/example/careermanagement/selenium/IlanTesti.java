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
        // İzin verdiğin tam adrese git
        driver.get("http://app:8080/kariyer/hedef/yeni.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Form elemanlarını bekle ve doldur
        WebElement baslik = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("baslik")));
        baslik.clear();
        baslik.sendKeys("Selenium Test Başlığı");

        WebElement aciklama = driver.findElement(By.id("aciklama"));
        aciklama.clear();
        aciklama.sendKeys("Bu ilan otomatik test ile oluşturulmuştur.");

        // Kaydet butonuna tıkla
        driver.findElement(By.id("kaydet")).click();

        // KRİTİK DÜZELTME: 'visibilityOf' yerine 'presenceOf' kullanıyoruz
        // Çünkü element style="display:none" olsa bile DOM'da vardır.
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mesaj")));

        System.out.println("✅ Selenium 5: İlan kayıt işlemi tetiklendi!");
    }
}