package com.example.careermanagement.security;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class YetkisizGirisHatasi implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(YetkisizGirisHatasi.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logger.error("Yetkisiz giriş hatası: {}", authException.getMessage());
        String uri = request.getRequestURI();
        // If the request looks like an API call, return 401; otherwise redirect to the login page so browsers see the login form.
        if (uri != null && uri.startsWith("/api/")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Hata: Yetkiniz yok!");
        } else {
            response.sendRedirect("/giris");
        }
    }
}