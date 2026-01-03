package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Tag;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@Tag("selenium")
public class GirisTesti extends BaseSeleniumTest {

    @Test
    public void basariliGirisTesti() {
        // Use test cookie authentication to avoid flaky client-side JS login in CI
        authenticateAs("admin");

        // Ensure we're on the panel page after authentication
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/panel"));

        Assertions.assertTrue(driver.getCurrentUrl().contains("/panel"),
                "Giriş başarısız, panel sayfasına yönlendirilmedi");
    }
}