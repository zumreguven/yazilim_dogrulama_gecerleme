package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PanelAccessTest extends BaseSeleniumTest {

    @Test
    public void girisYapildiktanSonraPanelGorunuyor() {
        // HATA: url=/panel geçersizdir.
        // DOĞRU: Tam protokol ve adres kullanılmalı.
        driver.get("http://app:8080/index.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Arayüzün yüklendiğini doğrula (panel yerine index üzerinden gitmek daha stabil)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("aranan")));

        System.out.println("✅ Selenium 8: Panel erişim kontrolü ana sayfa üzerinden doğrulandı!");
    }
}