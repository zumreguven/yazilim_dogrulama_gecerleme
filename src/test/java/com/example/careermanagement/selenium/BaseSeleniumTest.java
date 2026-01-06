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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class BaseSeleniumTest {
    protected WebDriver driver;

    // DİKKAT: Docker içindeyken "localhost" çalışmaz. 
    // Uygulama konteynerinin adı "app" olduğu için adres: http://app:8080 olmalı.
    protected final String BASE_URL = System.getProperty("app.baseUrl", "http://app:8080");

    @BeforeEach
    public void setUp() throws MalformedURLException {
        // Tarayıcı ayarları (Docker uyumlu)
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless"); // Ekran kartı olmadığı için şart
        options.addArguments("--no-sandbox"); // Güvenlik kısıtlamasını aş
        options.addArguments("--disable-dev-shm-usage"); // Bellek sorununu çöz
        options.addArguments("--remote-allow-origins=*"); // Bağlantı hatalarını önle
        options.addArguments("--window-size=1920,1080"); // Tam ekran gibi davran

        // Docker Compose dosyasındaki "selenium-hub" servisine bağlanıyoruz
        String hubUrl = "http://selenium-hub:4444/wd/hub";

        try {
            // RemoteWebDriver kullanarak uzaktaki konteynere bağlan
            driver = new RemoteWebDriver(new URL(hubUrl), options);
            System.out.println("✅ Selenium Hub'a başarıyla bağlanıldı: " + hubUrl);
        } catch (Exception e) {
            System.err.println("❌ Selenium Hub bağlantı hatası! URL: " + hubUrl);
            throw new RuntimeException("Selenium Hub'a bağlanılamadı. Docker servisi ayakta mı?", e);
        }

        // Sayfa yüklemeleri için bekleme süresi
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            // Hata durumunda ekran görüntüsü al (Opsiyonel ama faydalı)
            try {
                File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                Path destDir = Path.of("target", "selenium-screenshots");
                Files.createDirectories(destDir);
                String filename = getClass().getSimpleName() + "-" + System.currentTimeMillis() + ".png";
                Files.copy(scr.toPath(), destDir.resolve(filename));
            } catch (Exception e) {
                // Ekran görüntüsü alınamazsa testi durdurma
            }

            // Tarayıcıyı kapat
            driver.quit();
        }
    }

    // Test yardımcısı: Oturum açmış gibi cookie ekle
    protected void authenticateAs(String username) {
        try {
            driver.get(BASE_URL + "/"); // Önce domain'e git
            
            // Basit cookie ekleme
            Cookie authCookie = new Cookie("test-auth", username);
            driver.manage().addCookie(authCookie);
            
            // Panele git
            driver.get(BASE_URL + "/panel");
        } catch (Exception e) {
            System.err.println("Authentication cookie eklenirken hata: " + e.getMessage());
        }
    }
}