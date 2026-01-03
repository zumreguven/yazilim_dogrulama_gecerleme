package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

@Tag("selenium")
public class LogoutTest extends BaseSeleniumTest {

    @Test
    public void cikisIslemiCalisir() {
        // Authenticate directly to avoid client-side login flakes in CI
        authenticateAs("admin");

        // Click logout and verify redirect to login
        driver.findElement(By.cssSelector("a[href='/cikis']")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("kullaniciAdi")));

        assertThat(driver.getCurrentUrl()).contains("/giris");
    }

    // Keep the original UI login helper for local debugging if needed
    private void girisYap() {
        driver.get(BASE_URL + "/giris.html");
        driver.findElement(By.id("kullaniciAdi")).sendKeys("admin");
        driver.findElement(By.id("sifre")).sendKeys("sifre123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // Girişten sonra çıkış bağlantısının görünmesini bekle (daha stabil)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href='/cikis']")));
    }
}
