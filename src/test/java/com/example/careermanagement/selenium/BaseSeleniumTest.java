package com.example.careermanagement.selenium;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseSeleniumTest {
    protected WebDriver driver;
    // Make base URL configurable via -Dapp.baseUrl (default matches docker-compose.ci.yml)
    protected final String BASE_URL = System.getProperty("app.baseUrl", "http://localhost:8080");

    @BeforeEach
    public void setUp() throws MalformedURLException {
        // Tarayıcı ayarları
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(System.getProperty("selenium.headless", "true"))) {
            options.addArguments("--headless=new");
        }
        options.addArguments("--disable-gpu");
        options.addArguments("--no-sandbox");
        options.addArguments("--window-size=1920,1080");

        // If a remote Selenium hub is provided, use RemoteWebDriver with retries
        String remoteUrl = System.getProperty("selenium.remote.url");
        if (remoteUrl != null && !remoteUrl.isBlank()) {
            int attempts = 0;
            int maxAttempts = 8; // ~16s with 2s sleeps
            while (true) {
                try {
                    attempts++;
                    driver = new RemoteWebDriver(new URL(remoteUrl), options);
                    break;
                } catch (org.openqa.selenium.WebDriverException e) {
                    if (attempts >= maxAttempts) {
                        throw new RuntimeException("Failed to connect to Remote Selenium at " + remoteUrl + " after " + attempts + " attempts", e);
                    }
                    try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
                }
            }
        } else {
            // Fallback to local ChromeDriver (useful for local dev)
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            // Always try to capture a screenshot for debugging and reporting
            try {
                if (driver instanceof org.openqa.selenium.TakesScreenshot) {
                    java.io.File scr = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(org.openqa.selenium.OutputType.FILE);
                    java.nio.file.Path destDir = java.nio.file.Path.of("target", "selenium-screenshots");
                    java.nio.file.Files.createDirectories(destDir);
                    String filename = getClass().getSimpleName() + "-" + java.time.Instant.now().toString().replace(":", "-") + ".png";
                    java.nio.file.Files.copy(scr.toPath(), destDir.resolve(filename));
                }
            } catch (Exception e) {
                // ignore screenshot failures
            }

            driver.quit();
        }
    }

    // Test helper: mark the browser session as authenticated by setting a test cookie
    protected void authenticateAs(String username) {
        // Add a cookie that the test Jwt filter recognizes as authentication
        // Ensure we're on the app domain before adding the cookie
        driver.get(BASE_URL + "/");
        org.openqa.selenium.Cookie c = new org.openqa.selenium.Cookie.Builder("test-auth", username)
                .path("/")
                .isHttpOnly(false)
                .build();
        driver.manage().addCookie(c);
        // Visit a protected page to let the cookie be sent and the filter authenticate the session
        driver.get(BASE_URL + "/panel");
    }
}