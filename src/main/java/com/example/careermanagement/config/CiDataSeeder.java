package com.example.careermanagement.config;

import com.example.careermanagement.entity.Kullanici;
import com.example.careermanagement.entity.RolTuru;
import com.example.careermanagement.repository.KullaniciRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;

@Component
@Profile("ci")
public class CiDataSeeder {
    private static final Logger logger = LoggerFactory.getLogger(CiDataSeeder.class);

    private final KullaniciRepository kullaniciRepository;
    private final PasswordEncoder passwordEncoder;

    public CiDataSeeder(KullaniciRepository kullaniciRepository, PasswordEncoder passwordEncoder) {
        this.kullaniciRepository = kullaniciRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    @Transactional
    public void seed() {
        try {
            if (!kullaniciRepository.existsByKullaniciAdi("admin")) {
                logger.info("CI seeder: creating default admin user");
                Kullanici admin = new Kullanici();
                admin.setKullaniciAdi("admin");
                admin.setEposta("admin@example.com");
                admin.setAdSoyad("Admin User");
                admin.setSifre(passwordEncoder.encode("sifre123"));
                kullaniciRepository.save(admin);
            } else {
                logger.info("CI seeder: admin user already exists, skipping");
            }
        } catch (Exception e) {
            logger.warn("CI seeder failed to run: {}", e.toString());
        }
    }
}
