package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IlanTesti extends BaseSeleniumTest {
    @Test
    public void yeniIlanOlusturmaTesti() {
        // DIKKAT: Klasör yapısına göre adres güncellendi
        driver.get("http://app:8080/ilan/yeni.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Sayfa kaynağını kontrol et (Hata ayıklama için konsola yazar)
        System.out.println("📄 Sayfa başlığı: " + driver.getTitle());

        // Form ID'leri: baslik, aciklama, kaydet
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("baslik"))).sendKeys("Test Baslik");
        driver.findElement(By.id("aciklama")).sendKeys("Test Aciklama");
        driver.findElement(By.id("kaydet")).click();

        // Başarı mesajını bekle
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mesaj")));
        System.out.println("✅ Stage 5: İlan yolu doğrulandı ve oluşturuldu!");
    }
}