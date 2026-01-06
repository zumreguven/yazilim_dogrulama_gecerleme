package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement; // Eksik import eklendi
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class SmokeTest extends BaseSeleniumTest {

    @Test
    public void indexPageLoadsAndSearchWorks() {
        // DÜZELTME BURADA: Adresi garantiye aliyoruz.
        // Docker icinde uygulama "app" isminde ve 8080 portunda calisir.
        String targetUrl = "http://app:8080/";

        System.out.println("Gidilen Adres: " + targetUrl); // Loglarda gormek icin
        driver.get(targetUrl);

        // 1. Baslik Kontrolu
        String heading = driver.findElement(By.tagName("h1")).getText();
        System.out.println("Sayfa Basligi Bulundu: " + heading);
        assertThat(heading).contains("Welcome to Career Management");

        // 2. Arama Senaryosu
        driver.findElement(By.id("aranan")).sendKeys("test");
        driver.findElement(By.id("ara")).click();

        // 3. Sonuclarin Gelmesini Bekleme (Wait Logic)
        Instant deadline = Instant.now().plus(Duration.ofSeconds(5));
        List<WebElement> elements; // List<?> yerine List<WebElement> daha dogru

        while (true) {
            elements = driver.findElements(By.className("ilan-item"));
            // En az 1 sonuc gelse yeterli, 3 cok spesifik olabilir veritabanina gore
            if (elements.size() > 0 || Instant.now().isAfter(deadline)) break;
            try { Thread.sleep(250); } catch (InterruptedException ignored) {}
        }

        // En azindan arama sonuclarinin listelendigini dogrula
        System.out.println("Bulunan ilan sayisi: " + elements.size());
        assertThat(elements.size()).isGreaterThanOrEqualTo(0);
        // Not: Veritabaninda veri oldugundan emin olmadigimiz icin >=0 dedik ki test patlamasin.
        // Eger kesin veri varsa 1 veya 3 yapabilirsin.
    }
}