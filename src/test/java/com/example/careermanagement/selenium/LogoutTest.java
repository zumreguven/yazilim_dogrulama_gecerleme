package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("selenium")
public class LogoutTest extends BaseSeleniumTest {

    @Test
    public void cikisIslemiCalisir() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // 1. ADIM: Tam URL ile giriş sayfasına git ve oturum aç
        driver.get("http://app:8080/giris.html");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("kullaniciAdi"))).sendKeys("admin");
        driver.findElement(By.id("sifre")).sendKeys("sifre123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 2. ADIM: Çıkış işlemini tam URL ile tetikle
        driver.get("http://app:8080/cikis");

        // 3. ADIM: Tekrar giriş sayfasına (kullaniciAdi inputu olan sayfa) döndüğünü doğrula
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("kullaniciAdi")));

        assertThat(driver.getCurrentUrl()).contains("giris");
        System.out.println("✅ Selenium 10: Çıkış işlemi başarıyla tamamlandı!");
    }
}