package com.example.careermanagement.unit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class UserValidationTest {

    // Kullanıcı adı testleri
    @Test
    void whenUsernameIsValid_thenReturnTrue() {
        String username = "zumre123";
        assertTrue(isValidUsername(username), "Geçerli kullanıcı adı kabul edilmeli");
    }

    @Test
    void whenUsernameIsTooShort_thenReturnFalse() {
        String username = "ab";
        assertFalse(isValidUsername(username), "Çok kısa kullanıcı adı kabul edilmemeli");
    }

    @Test
    void whenUsernameContainsSpecialChars_thenReturnFalse() {
        String username = "zumre@123";
        assertFalse(isValidUsername(username), "Özel karakter içeren kullanıcı adı kabul edilmemeli");
    }

    // E-posta testleri
    @ParameterizedTest
    @ValueSource(strings = {
            "test@example.com",
            "user.name@domain.co",
            "user.name+tag@domain.co.tr"
    })
    void whenEmailIsValid_thenReturnTrue(String email) {
        assertTrue(isValidEmail(email), "Geçerli e-posta adresi kabul edilmeli: " + email);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "invalid-email",
            "user@.com",
            "@domain.com",
            "user@domain..com"
    })
    void whenEmailIsInvalid_thenReturnFalse(String email) {
        assertFalse(isValidEmail(email), "Geçersiz e-posta adresi reddedilmeli: " + email);
    }

    // Şifre testleri
    @Test
    void whenPasswordIsStrong_thenReturnTrue() {
        String password = "Sifre123!";
        assertTrue(isValidPassword(password), "Güçlü şifre kabul edilmeli");
    }

    @Test
    void whenPasswordIsTooShort_thenReturnFalse() {
        String password = "Sif1!";
        assertFalse(isValidPassword(password), "Çok kısa şifre kabul edilmemeli");
    }

    @Test
    void whenPasswordMissingUppercase_thenReturnFalse() {
        String password = "sifre123!";
        assertFalse(isValidPassword(password), "Büyük harf içermeyen şifre kabul edilmemeli");
    }

    // Yardımcı doğrulama metodları
    private boolean isValidUsername(String username) {
        return username != null &&
                username.length() >= 3 &&
                username.matches("^[a-zA-Z0-9]+$");
    }

    private boolean isValidEmail(String email) {
        return email != null &&
                !email.contains("..") &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean isValidPassword(String password) {
        return password != null &&
                password.length() >= 8 &&
                password.matches(".*[A-Z].*") &&    // En az bir büyük harf
                password.matches(".*[a-z].*") &&    // En az bir küçük harf
                password.matches(".*\\d.*") &&      // En az bir rakam
                password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*"); // En az bir özel karakter
    }
}