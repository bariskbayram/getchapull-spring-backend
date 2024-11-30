package com.bkb.metalmusicreviews.backend.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtTokenVerifier extends OncePerRequestFilter {

    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    public JwtTokenVerifier(SecretKey secretKey,
                            JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Authorization key'i ile yollamıştık. Geri aynı şekilde geliyor ve aldık
        String authorizationHeader = request.getHeader("Authorization");

        System.out.println("authorization headerdan alındı");

        if(Strings.isNullOrEmpty(authorizationHeader) || !authorizationHeader.startsWith(jwtConfig.getTokenPrefix())){
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " etkiketini sildik, geriye Token kaldı
        String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");

        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            // body kısmını aldık yani payload - jwt.io ile bakabilirsin
            Claims body = claimsJws.getBody();

            // body'de subject olarak username tutulur "bariskbayram" gibi
            String username = body.getSubject();

            // body'de authorities olarak array var onu da aldık.
            List<Map<String,String>> authorities = (List<Map<String, String>>) body.get("authorities");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map( m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );

            System.out.println("token doğrulandı");

            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (JwtException e){
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }

        // filtreleme işlemi sırayla çalıştığı için ürettiği sonucu bir sonraki filter'a ulaştırmalı
        filterChain.doFilter(request, response);
    }
}
