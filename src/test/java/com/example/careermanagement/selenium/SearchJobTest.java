package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("selenium")
public class SearchJobTest extends BaseSeleniumTest {

    @Test
    public void isIlanlariFiltrelemeIslemi() {
        driver.get(BASE_URL + "/index.html");
        // Arama alanına bir terim gir ve listeyi kontrol et
        driver.findElement(By.id("aranan")).sendKeys("Yazılım");
        driver.findElement(By.id("ara")).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".ilan-item")));

        List<WebElement> ilanlar = driver.findElements(By.cssSelector(".ilan-item"));
        assertThat(ilanlar.size()).isGreaterThanOrEqualTo(1);
    }
}
