package com.example.careermanagement.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.Cookie;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.io.File;

public class BaseSeleniumTest {
    protected WebDriver driver;

    // Uygulama adresini al
    protected final String BASE_URL = System.getProperty("app.baseUrl", "http://app:8080");

    @BeforeEach
    public void setUp() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--window-size=1920,1080");

        // BURASI KRİTİK: Önce ortam değişkenine bak, yoksa localhost'a git.
        // Docker içindeyken ortam değişkeni dolu gelecek ve doğru çalışacak.
        String hubUrl = System.getenv("SELENIUM_REMOTE_URL");
        if (hubUrl == null || hubUrl.isEmpty()) {
            hubUrl = "http://localhost:4444/wd/hub";
        }

        System.out.println("🔗 Selenium Hub Hedef Adresi: " + hubUrl);

        try {
            driver = new RemoteWebDriver(new URL(hubUrl), options);
            System.out.println("✅ Selenium Hub'a bağlanıldı!");
        } catch (Exception e) {
            System.err.println("❌ Selenium Hub bağlantı hatası! URL: " + hubUrl);
            throw new RuntimeException("Selenium Hub'a bağlanılamadı.", e);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            try {
                // Ekran görüntüsü alma kodu (basitleştirildi)
                File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                // Burada dosya kaydetme işlemleri yapılabilir
            } catch (Exception e) {
                // Hata yutulur
            }
            driver.quit();
        }
    }

    protected void authenticateAs(String username) {
        try {
            driver.get(BASE_URL + "/");
            Cookie authCookie = new Cookie("test-auth", username);
            driver.manage().addCookie(authCookie);
            driver.get(BASE_URL + "/panel");
        } catch (Exception e) {
            System.err.println("Auth hatası: " + e.getMessage());
        }
    }
}