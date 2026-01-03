package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class PanelAccessTest extends BaseSeleniumTest {

    @Test
    public void girisYapildiktanSonraPanelGorunuyor() {
        girisYap();
        driver.get(BASE_URL + "/panel");

        // Panelde çıkış linkinin görünür olması beklenir
        String cikisText = driver.findElement(By.id("cikis")).getText();
        assertThat(cikisText).contains("Çıkış");
    }

    private void girisYap() {
        driver.get(BASE_URL + "/giris.html");
        driver.findElement(By.id("kullaniciAdi")).sendKeys("admin");
        driver.findElement(By.id("sifre")).sendKeys("sifre123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }
}
