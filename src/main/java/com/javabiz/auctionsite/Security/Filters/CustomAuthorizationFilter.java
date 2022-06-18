package com.javabiz.auctionsite.Security.Filters;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        if (Strings.isEmpty(authorizationHeader) || authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // odrzucony request bo nie ma autoryzacji
            return;
        }

        String token = authorizationHeader.replace("Bearer ", "");

        try {
            var claimJws = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor("securesecuresecuresecuresecuresecuresecuresecuresecuresecure".getBytes()))
                    .build()
                    .parseClaimsJws(token);

            var body = claimJws.getBody();
            String username = body.getSubject();
            var authorirites = (List<Map<String, String>>) body.get("authorirites");

            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorirites.stream()
                    .map(m -> new SimpleGrantedAuthority(m.get("authority")))
                    .collect(Collectors.toSet());

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);

            SecurityContextHolder.getContext().setAuthentication(authentication); // user dostaje dostep
        } catch (JwtException ex) {
            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
        }

        filterChain.doFilter(request, response);

    }
}