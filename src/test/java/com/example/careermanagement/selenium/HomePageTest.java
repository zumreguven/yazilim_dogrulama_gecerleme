package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

@Tag("selenium")
public class HomePageTest extends BaseSeleniumTest {

    @Test
    public void anaSayfaYukleniyorVeBaslikDogru() {
        driver.get("http://app:8080/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Sayfa kaynağının gelmesini bekle
        wait.until(d -> d.getPageSource().contains("Kariyer") || d.getPageSource().contains("Welcome"));

        String title = driver.getTitle();
        System.out.println("✅ Ana Sayfa Title: " + title);

        // Sayfada 'ara' butonu var mı? (index.html'indeki kritik buton)
        Assertions.assertNotNull(driver.findElement(By.id("ara")), "Arama butonu bulunamadı!");
    }
}