package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class HomePageTest extends BaseSeleniumTest {
    @Test
    public void anaSayfaYukleniyorVeAraButonuVar() {
        // index.html sayfasına git
        driver.get("http://app:8080/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Senin HTML'indeki ID'ler: "aranan" ve "ara"
        // Önce kutunun görünmesini bekle
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("aranan")));
        // Sonra butonun tıklanabilir olmasını bekle (Build #139 burada hata vermişti)
        wait.until(ExpectedConditions.elementToBeClickable(By.id("ara")));

        System.out.println("✅ Selenium 4: Ana sayfa ve Ara butonu doğrulandı!");
    }
}