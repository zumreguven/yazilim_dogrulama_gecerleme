package com.example.careermanagement.selenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IlanTesti extends BaseSeleniumTest {

    @Test
    public void yeniIlanOlusturmaTesti() {
        // 1. Önce giriş yap
        girisYap();

        // 2. İlan oluştur sayfasına git
        driver.get(BASE_URL + "/ilan/yeni");

        // 3. Formu doldur
        driver.findElement(By.id("baslik")).sendKeys("Yazılım Uzmanı");
        driver.findElement(By.id("aciklama")).sendKeys("Aranıyor yazılım uzmanı");
        driver.findElement(By.id("kaydet")).click();

        // 4. Başarı mesajını kontrol et
        String mesaj = driver.findElement(By.cssSelector(".alert-success")).getText();
        Assert.assertTrue(mesaj.contains("başarıyla oluşturuldu"), "İlan oluşturulamadı");
    }

    private void girisYap() {
        driver.get(BASE_URL + "/giris");
        driver.findElement(By.id("kullaniciAdi")).sendKeys("admin");
        driver.findElement(By.id("sifre")).sendKeys("sifre123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }
}