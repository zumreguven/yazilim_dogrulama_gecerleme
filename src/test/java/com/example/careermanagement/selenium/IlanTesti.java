package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IlanTesti extends BaseSeleniumTest {
    @Test
    public void yeniIlanOlusturmaTesti() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // 1. ÖNCE GİRİŞ YAP (Security engeline takılmamak için)
        driver.get("http://app:8080/giris.html");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kullaniciAdi"))).sendKeys("admin");
        driver.findElement(By.id("sifre")).sendKeys("sifre123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 2. ŞİMDİ İLAN SAYFASINA GİT
        driver.get("http://app:8080/ilan/yeni.html");

        // 3. FORM DOLDUR (Şimdi 'baslik' görünür olacak)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("baslik"))).sendKeys("Test Mühendisi");
        driver.findElement(By.id("aciklama")).sendKeys("Selenium uzmanı.");
        driver.findElement(By.id("kaydet")).click();

        // 4. BAŞARI MESAJINI DOĞRULA
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mesaj")));
        System.out.println("✅ Selenium 5: Giriş yapıldı ve ilan başarıyla oluşturuldu!");
    }
}