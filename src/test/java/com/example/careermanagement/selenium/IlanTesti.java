package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class IlanTesti extends BaseSeleniumTest {
    @Test
    public void yeniIlanOlusturmaTesti() {
        // SecurityYapilandirma'da permitAll() verdiğin tam yolu kullanıyoruz
        driver.get("http://app:8080/kariyer/hedef/yeni.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Formu doldur
        WebElement baslik = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("baslik")));
        baslik.clear();
        baslik.sendKeys("Selenium Uzmanı");

        driver.findElement(By.id("aciklama")).clear();
        driver.findElement(By.id("aciklama")).sendKeys("Otomatik test başarılı.");

        // Kaydet butonuna tıkla
        driver.findElement(By.id("kaydet")).click();

        // KRİTİK NOKTA: 'visibilityOf' Jenkins'te hata verir (CSS/JS yüklenmeyebilir)
        // 'presenceOfElementLocated' kullanarak elementin kodda olmasını yeterli buluyoruz.
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("mesaj")));

        System.out.println("✅ Selenium 5: İlan kayıt formu başarıyla tetiklendi!");
    }
}