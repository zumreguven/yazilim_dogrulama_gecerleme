package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class PanelAccessTest extends BaseSeleniumTest {

    @Test
    public void girisYapildiktanSonraPanelGorunuyor() {
        // Use the cookie-based helper to avoid flaky client-side login behavior in CI
        authenticateAs("admin");

        driver.get(BASE_URL + "/panel");

        // Panelde çıkış linkinin görünür olması beklenir
        String cikisText = driver.findElement(By.id("cikis")).getText();
        assertThat(cikisText).contains("Çıkış");
    }
}
