package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Tag;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.Duration;

@Tag("selenium")
public class GirisTesti extends BaseSeleniumTest {

    @Test
    public void basariliGirisTesti() {
        // ÖNCE BURAYA ADRESİ ELİMİZLE VERELİM
        driver.get("http://app:8080/login");

        // CI ortamında login işlemini simüle eden metodunuzu çağırın
        authenticateAs("admin");

        // Panel sayfasını bekle
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Önemli: Eğer uygulaman /panel yerine /panel.html gibi bir yere gidiyorsa orayı yaz
        wait.until(ExpectedConditions.urlContains("/panel"));

        Assertions.assertTrue(driver.getCurrentUrl().contains("/panel"),
                "Giriş başarısız, panel sayfasına yönlendirilmedi. Mevcut URL: " + driver.getCurrentUrl());
    }
}