package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.junit.jupiter.api.Assertions;

@Tag("selenium")
public class HomePageTest extends BaseSeleniumTest {

    @Test
    public void anaSayfaYukleniyorVeBaslikDogru() {
        // HATA BURADAYDI: /index.html yerine tam adres veriyoruz
        String targetUrl = "http://app:8080/";
        System.out.println("🚀 Ana sayfa testi gidilen adres: " + targetUrl);
        driver.get(targetUrl);

        // Senin HTML kodunda <h1>Welcome...</h1> yazıyor, onu kontrol edelim
        WebElement header = driver.findElement(By.tagName("h1"));
        String headerText = header.getText();

        System.out.println("✅ Ana sayfa başlığı bulundu: " + headerText);
        Assertions.assertTrue(headerText.contains("Welcome"), "Başlık metni hatalı!");
    }
}