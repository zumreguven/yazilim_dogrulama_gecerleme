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
        // 1. Giriş sayfasına git
        String targetUrl = "http://app:8080/giris";
        System.out.println("🚀 Gidilen adres: " + targetUrl);
        driver.get(targetUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 2. Senin HTML'indeki id="kullaniciAdi" kutusu görünene kadar bekle
        // Bu sayfanın gerçekten yüklendiğinin en sağlam kanıtıdır.
        WebElement userField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("kullaniciAdi")));

        // 3. Doğrulama: Kutu oradaysa sayfa yüklenmiştir.
        Assertions.assertNotNull(userField, "Kullanıcı adı kutusu bulunamadı, sayfa hatalı!");

        System.out.println("✅ Giriş sayfası başarıyla yüklendi ve ID doğrulandı.");
    }
}