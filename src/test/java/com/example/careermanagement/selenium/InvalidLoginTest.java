package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class InvalidLoginTest extends BaseSeleniumTest {

    @Test
    public void hataliGirisMesajiGosterir() {
        driver.get(BASE_URL + "/giris.html");
        driver.findElement(By.id("kullaniciAdi")).sendKeys("wrong");
        driver.findElement(By.id("sifre")).sendKeys("wrong");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String mesaj = driver.findElement(By.cssSelector(".alert-danger")).getText();
        assertThat(mesaj.toLowerCase()).contains("geçersiz");
    }
}
