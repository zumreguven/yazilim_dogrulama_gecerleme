package com.example.careermanagement.selenium;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GirisTesti extends BaseSeleniumTest {

    @Test
    public void basariliGirisTesti() {
        // 1. Giriş sayfasına git
        driver.get(BASE_URL + "/giris");

        // 2. Kullanıcı adı ve şifre gir
        driver.findElement(By.id("kullaniciAdi")).sendKeys("admin");
        driver.findElement(By.id("sifre")).sendKeys("sifre123");

        // 3. Giriş butonuna tıkla
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        // 4. Başarılı giriş kontrolü
        Assert.assertTrue(driver.getCurrentUrl().contains("/panel"),
                "Giriş başarısız, panel sayfasına yönlendirilmedi");
    }
}