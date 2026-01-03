package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class CareerGoalTest extends BaseSeleniumTest {

    @Test
    public void yeniKariyerHedefiOlustur() {
        girisYap();
        driver.get(BASE_URL + "/kariyer/hedef/yeni");

        driver.findElement(By.id("baslik")).sendKeys("Kariyer Hedefi 1");
        driver.findElement(By.id("aciklama")).sendKeys("Açıklama");
        driver.findElement(By.id("kaydet")).click();

        String mesaj = driver.findElement(By.cssSelector(".alert-success")).getText();
        assertThat(mesaj).contains("başarıyla oluşturuldu");
    }

    private void girisYap() {
        driver.get(BASE_URL + "/giris.html");
        driver.findElement(By.id("kullaniciAdi")).sendKeys("admin");
        driver.findElement(By.id("sifre")).sendKeys("sifre123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }
}
