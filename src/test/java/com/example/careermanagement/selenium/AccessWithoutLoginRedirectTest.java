package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AccessWithoutLoginRedirectTest extends BaseSeleniumTest {
    @Test
    public void panelErisimiGirisFormunuGosteriyor() {
        // HATA: get("/panel") -> DOĞRU: Tam Protokollü URL
        driver.get("http://app:8080/panel.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Sistem STATELESS olduğu için giriş sayfasına yönlendirmeli
        // Giriş sayfasındaki kullanıcı adı alanını bekle
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("kullaniciAdi")));

        System.out.println("✅ Selenium 9: Girişsiz erişim engeli ve yönlendirme doğrulandı!");
    }
}