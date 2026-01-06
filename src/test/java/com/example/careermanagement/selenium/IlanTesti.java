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

        // Form elemanlarını bekle ve doldur
        WebElement baslik = wait.until(ExpectedConditions.elementToBeClickable(By.id("baslik")));
        baslik.sendKeys("Test Uzmanı");

        driver.findElement(By.id("aciklama")).sendKeys("Otomasyon testi yapabilen.");
        driver.findElement(By.id("kaydet")).click();

        // Başarı mesajını bekle
        WebElement mesaj = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mesaj")));
        Assertions.assertTrue(mesaj.getText().contains("başarıyla"), "Kayıt işlemi başarısız!");
    }
}