package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class HomePageTest extends BaseSeleniumTest {

    @Test
    public void anaSayfaYukleniyorVeBaslikDogru() {
        driver.get(BASE_URL + "/index.html");

        String title = driver.getTitle();
        assertThat(title).contains("Career");

        String header = driver.findElement(By.tagName("h1")).getText();
        assertThat(header).contains("Welcome");
    }
}
