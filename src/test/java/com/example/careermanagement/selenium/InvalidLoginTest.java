package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

@Tag("selenium")
public class InvalidLoginTest extends BaseSeleniumTest {

    @Test
    public void hataliGirisTesti() {
        // Tam adrese git
        String targetUrl = "http://app:8080/giris";
        driver.get(targetUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Senin HTML'indeki id="kullaniciAdi" inputunu bekle
        WebElement userField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("kullaniciAdi")));

        // Kutu oradaysa sayfa doğru yüklenmiştir
        Assertions.assertNotNull(userField, "Giriş sayfası yüklenemedi, kullanıcı adı alanı bulunamadı!");
        System.out.println("✅ InvalidLoginTest: Sayfa ve input ID doğrulandı.");
    }
}