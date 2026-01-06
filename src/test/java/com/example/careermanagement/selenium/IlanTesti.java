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
        driver.get("http://app:8080/yen");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // 1. Başlık kutusu gelene kadar bekle ve yaz
        WebElement baslik = wait.until(ExpectedConditions.elementToBeClickable(By.id("baslik")));
        baslik.clear();
        baslik.sendKeys("Yazılım Mühendisi");

        // 2. Açıklama kutusunu doldur
        WebElement aciklama = driver.findElement(By.id("aciklama"));
        aciklama.clear();
        aciklama.sendKeys("Java ve Selenium bilen adaylar aranıyor.");

        // 3. Kaydet butonuna bas
        WebElement kaydet = driver.findElement(By.id("kaydet"));
        kaydet.click();

        // 4. Mesajın (id="mesaj") görünmesini bekle ve doğrula
        WebElement sonucMesaji = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mesaj")));
        System.out.println("✅ İlan Mesajı: " + sonucMesaji.getText());

        Assertions.assertTrue(sonucMesaji.getText().contains("başarıyla"), "İlan oluşturma başarısız!");
    }
}