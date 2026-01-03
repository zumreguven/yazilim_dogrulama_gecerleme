package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SmokeTest extends BaseSeleniumTest {

    @Test
    public void indexPageLoadsAndSearchWorks() {
        driver.get(BASE_URL + "/");

        String heading = driver.findElement(By.tagName("h1")).getText();
        assertThat(heading).contains("Welcome to Career Management");

        // Search flow: type a term, click search, expect 3 results to appear
        driver.findElement(By.id("aranan")).sendKeys("test");
        driver.findElement(By.id("ara")).click();

        // Wait up to 5s for 3 .ilan-item elements to appear
        Instant deadline = Instant.now().plus(Duration.ofSeconds(5));
        List elements;
        while (true) {
            elements = driver.findElements(By.className("ilan-item"));
            if (elements.size() >= 3 || Instant.now().isAfter(deadline)) break;
            try { Thread.sleep(250); } catch (InterruptedException ignored) {}
        }

        assertThat(elements.size()).isGreaterThanOrEqualTo(3);
    }
}
