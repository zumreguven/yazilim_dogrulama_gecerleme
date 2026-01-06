package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SearchJobTest extends BaseSeleniumTest {
    @Test
    public void isIlanlariFiltrelemeIslemi() {
        driver.get("http://app:8080/index.html"); // permitAll() listesinde var ✅
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("aranan"))).sendKeys("Yazılım");
        driver.findElement(By.id("ara")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ilan-item")));
        System.out.println("✅ Selenium 6: Herkese açık arama sayfası doğrulandı.");
    }
}