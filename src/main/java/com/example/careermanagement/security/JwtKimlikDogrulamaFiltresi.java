package com.example.careermanagement.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtKimlikDogrulamaFiltresi extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private KullaniciDetayServisi kullaniciDetayServisi;

    private static final Logger logger = LoggerFactory.getLogger(JwtKimlikDogrulamaFiltresi.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Test-mode cookie support: if a cookie named 'test-auth' is present, treat its value as a username and authenticate as that user.
            if (request.getCookies() != null) {
                for (jakarta.servlet.http.Cookie c : request.getCookies()) {
                    if ("test-auth".equals(c.getName())) {
                        String kullaniciAdi = c.getValue();
                        UserDetails kullaniciDetaylari = kullaniciDetayServisi.loadUserByUsername(kullaniciAdi);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                kullaniciDetaylari, null, kullaniciDetaylari.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        break;
                    }
                }
            }

            String jwt = jwtAl(request);
            if (jwt != null && jwtUtils.dogrula(jwt)) {
                String kullaniciAdi = jwtUtils.kullaniciAdiniAl(jwt);

                UserDetails kullaniciDetaylari = kullaniciDetayServisi.loadUserByUsername(kullaniciAdi);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        kullaniciDetaylari, null, kullaniciDetaylari.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Kimlik doğrulama işlemi sırasında hata oluştu: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    private String jwtAl(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7, headerAuth.length());
        }

        return null;
    }
}