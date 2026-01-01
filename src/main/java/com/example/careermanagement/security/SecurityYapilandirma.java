package com.example.careermanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityYapilandirma {

    private final KullaniciDetayServisi kullaniciDetayServisi;
    private final YetkisizGirisHatasi yetkisizGirisHatasi;

    public SecurityYapilandirma(KullaniciDetayServisi kullaniciDetayServisi,
                                YetkisizGirisHatasi yetkisizGirisHatasi) {
        this.kullaniciDetayServisi = kullaniciDetayServisi;
        this.yetkisizGirisHatasi = yetkisizGirisHatasi;
    }

    @Bean
    public JwtKimlikDogrulamaFiltresi kimlikDogrulamaJwtTokenFiltresi() {
        return new JwtKimlikDogrulamaFiltresi();
    }

    @Bean
    public PasswordEncoder sifreKodlayici() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CORS ayarları
        http.cors(AbstractHttpConfigurer::disable);

        // CSRF devre dışı
        http.csrf(AbstractHttpConfigurer::disable);

        // Hata yönetimi
        http.exceptionHandling(exception ->
                exception.authenticationEntryPoint(yetkisizGirisHatasi)
        );

        // Oturum yönetimi (stateless)
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Yetkilendirme kuralları
        http.authorizeHttpRequests(auth -> auth
                // Halka açık endpoint'ler
                .requestMatchers(
                        "/",
                        "/index.html",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/test/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()

                // Tüm diğer istekler için kimlik doğrulama gerekli
                .anyRequest().authenticated()
        );

        // H2 Console için frame options
        http.headers(headers ->
                headers.frameOptions(frame -> frame.sameOrigin())
        );

        // JWT Filtresini ekle
        http.addFilterBefore(
                kimlikDogrulamaJwtTokenFiltresi(),
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}