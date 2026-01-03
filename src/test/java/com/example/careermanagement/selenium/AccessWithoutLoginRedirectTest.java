package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class AccessWithoutLoginRedirectTest extends BaseSeleniumTest {

    @Test
    public void panelErisimiGirisFormunuGosteriyor() {
        // Giriş yapılmadan panel sayfasına git
        driver.get(BASE_URL + "/panel");

        // Kullanıcı adı alanı görünür olmalı (giriş formu)
        boolean hasKullaniciAdi = driver.findElements(By.id("kullaniciAdi")).size() > 0;
        assertThat(hasKullaniciAdi).isTrue();
    }
}
