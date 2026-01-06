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
public class IlanTesti extends BaseSeleniumTest {

    @Test
    public void yeniIlanOlusturmaTesti() {
        // 1. Yeni ilan sayfasına git (yen.html adresi)
        String targetUrl = "http://app:8080/yen";
        driver.get(targetUrl);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 2. Senin HTML'indeki id="baslik" ve id="aciklama" kutularını doldur
        WebElement baslikKutusu = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("baslik")));
        WebElement aciklamaKutusu = driver.findElement(By.id("aciklama"));
        WebElement kaydetButonu = driver.findElement(By.id("kaydet"));

        baslikKutusu.sendKeys("Yazılım Test Mühendisi");
        aciklamaKutusu.sendKeys("Jenkins ve Selenium bilen çalışma arkadaşları aranıyor.");

        // 3. Kaydet'e bas
        kaydetButonu.click();

        // 4. Başarı mesajının (id="mesaj") görünmesini bekle
        WebElement basariMesaji = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mesaj")));

        // 5. Mesajın içeriğini senin HTML'indeki "İlan başarıyla oluşturuldu" yazısıyla karşılaştır
        String mesajMetni = basariMesaji.getText();
        System.out.println("✅ İlan Sonuç Mesajı: " + mesajMetni);

        Assertions.assertTrue(mesajMetni.contains("başarıyla oluşturuldu"), "İlan oluşturma mesajı hatalı!");
    }
}