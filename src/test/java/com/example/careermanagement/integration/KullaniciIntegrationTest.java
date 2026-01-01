package com.example.careermanagement.integration;

import com.example.careermanagement.entity.Kullanici;
import com.example.careermanagement.repository.KullaniciRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class KullaniciIntegrationTest {

    @Autowired
    private KullaniciRepository kullaniciRepository;

    private String uniqueUsername;
    private String uniqueEmail;

    @BeforeEach
    public void setup() {
        // Her test için benzersiz kullanıcı adı ve e-posta oluştur
        String unique = UUID.randomUUID().toString().substring(0, 8);
        uniqueUsername = "testuser_" + unique;
        uniqueEmail = "test_" + unique + "@example.com";
    }

    @AfterEach
    public void cleanup() {
        // Test sonrası temizlik
        kullaniciRepository.deleteAll();
    }

    @Test
    public void whenSaveKullanici_thenKullaniciIsSaved() {
        // Given
        Kullanici kullanici = new Kullanici();
        kullanici.setKullaniciAdi(uniqueUsername);
        kullanici.setEposta(uniqueEmail);
        kullanici.setSifre("sifre123");
        kullanici.setAdSoyad("Test Kullanıcı");

        // When
        Kullanici kaydedilenKullanici = kullaniciRepository.save(kullanici);
        Optional<Kullanici> bulunan = kullaniciRepository.findById(kaydedilenKullanici.getKimlik());

        // Then
        assertTrue(bulunan.isPresent());
        assertEquals(uniqueUsername, bulunan.get().getKullaniciAdi());
        assertEquals(uniqueEmail, bulunan.get().getEposta());
    }

    @Test
    public void whenFindByEposta_thenReturnKullanici() {
        // Given
        Kullanici kullanici = new Kullanici();
        kullanici.setKullaniciAdi(uniqueUsername);
        kullanici.setEposta(uniqueEmail);
        kullanici.setSifre("sifre123");
        kullanici.setAdSoyad("Test Kullanıcı");
        kullaniciRepository.save(kullanici);

        // When
        Optional<Kullanici> bulunan = kullaniciRepository.findByEposta(uniqueEmail);

        // Then
        assertTrue(bulunan.isPresent());
        assertEquals(uniqueUsername, bulunan.get().getKullaniciAdi());
    }
}