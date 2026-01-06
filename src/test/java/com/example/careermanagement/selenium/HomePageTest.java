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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Önce sayfa kaynağında anahtar bir kelime bekleyelim
        wait.until(d -> d.getPageSource().contains("Kariyer") || d.getPageSource().contains("Welcome"));

        // 'ara' butonu görünür olana kadar bekle (Hata buradaydı!)
        WebElement araButonu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("ara")));

        System.out.println("✅ Ana Sayfa ve Arama butonu doğrulandı.");
        Assertions.assertNotNull(araButonu, "Arama butonu sayfada mevcut değil!");
    }
}