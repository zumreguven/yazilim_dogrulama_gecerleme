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
public class HomePageTest extends BaseSeleniumTest {

    @Test
    public void anaSayfaYukleniyorVeBaslikDogru() {
        driver.get("http://app:8080/");

        // Sayfanın yüklenmesi için 10 saniye tolerans tanıyalım
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // h1 etiketini bulana kadar bekle
        WebElement header = wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("h1")));

        System.out.println("✅ Ana sayfa başlığı doğrulandı: " + header.getText());
        Assertions.assertTrue(header.getText().contains("Welcome"), "Başlık hatalı!");
    }
}