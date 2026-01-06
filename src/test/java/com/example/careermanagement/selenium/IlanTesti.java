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

        // 1. ADIM: Kullanıcı girişi (Giriş yapmadan ilan ekleme yetkisi yoktur)
        driver.get("http://app:8080/giris.html");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("kullaniciAdi"))).sendKeys("admin");
        driver.findElement(By.id("sifre")).sendKeys("sifre123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 2. ADIM: Giriş başarılı olduktan sonra ilan klasöründeki sayfaya git
        driver.get("http://app:8080/ilan/yeni.html");

        // 3. ADIM: Formu doldur (Artık 'baslik' elementi bulunabilir)
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("baslik"))).sendKeys("Kullanıcı Test İlanı");
        driver.findElement(By.id("aciklama")).sendKeys("Kullanıcı tarafından Selenium ile eklendi.");

        // 4. ADIM: Kaydet butonuna bas
        driver.findElement(By.id("kaydet")).click();

        // 5. ADIM: Başarı mesajını doğrula
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mesaj")));
        System.out.println("✅ Selenium 5: Giriş yapıldı ve ilan başarıyla oluşturuldu.");
    }
}