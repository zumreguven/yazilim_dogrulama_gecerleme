package com.example.careermanagement.selenium;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("selenium")
public class SearchJobTest extends BaseSeleniumTest {

    @Test
    public void isIlanlariFiltrelemeIslemi() {
        // Garantici adres: index.html
        driver.get("http://app:8080/index.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // 1. Arama kutusunun gelmesini bekle ve metni gönder
        WebElement aramaKutusu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("aranan")));
        aramaKutusu.clear();
        aramaKutusu.sendKeys("Yazılım");

        // 2. Ara butonuna tıkla
        driver.findElement(By.id("ara")).click();

        // 3. JavaScript'in ilanları listeye eklemesini bekle (ilan-item class'ı)
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("ilan-item")));

        // 4. Listeyi kontrol et
        List<WebElement> ilanlar = driver.findElements(By.className("ilan-item"));
        assertThat(ilanlar.size()).isGreaterThanOrEqualTo(1);

        System.out.println("✅ Selenium 6: İş arama ve filtreleme başarıyla doğrulandı!");
    }
}