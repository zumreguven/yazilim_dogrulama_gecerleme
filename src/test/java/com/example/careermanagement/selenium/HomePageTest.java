package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePageTest extends BaseSeleniumTest {
    @Test
    public void anaSayfaYukleniyorVeAraButonuVar() {
        // Garantici adres: Direkt dosyaya gitmeyi deniyoruz
        driver.get("http://app:8080/index.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        try {
            // Önce sayfanın gerçekten yüklendiğinden emin olalım
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("aranan")));
            System.out.println("✅ Stage 4: 'aranan' ID bulundu!");
        } catch (Exception e) {
            System.out.println("❌ HATA: Element bulunamadı! Sayfa içeriği: " + driver.getPageSource());
            // Eğer index.html çalışmazsa kök dizini son kez dene
            driver.get("http://app:8080/");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("aranan")));
        }
    }
}