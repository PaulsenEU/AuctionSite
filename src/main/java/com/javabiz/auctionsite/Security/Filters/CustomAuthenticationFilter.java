package com.javabiz.auctionsite.Security.Filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserAndPasswordAuthRequest usernameAndPasswordAuthRequest =
                    new ObjectMapper().readValue(request.getInputStream(), UserAndPasswordAuthRequest.class);

            Authentication authenticate = new UsernamePasswordAuthenticationToken(
                    usernameAndPasswordAuthRequest.getUsername(),
                    usernameAndPasswordAuthRequest.getPassword()
            );

            return authenticationManager.authenticate(authenticate);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * This method generates a JSON Web Token after successful authentication (when successfully logged) - expiring after 2 weeks from login (or after backend app restart).
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorirites", authResult.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(Keys.hmacShaKeyFor("securesecuresecuresecuresecuresecuresecuresecuresecuresecure".getBytes()))
                .compact();

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("access_token", "Bearer " + token);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokenMap);
    }
}