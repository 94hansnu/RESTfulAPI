package com.example.RESTfulAPI.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class TokenService {

    // Injicering av JwtEncoder för att koda JWT-token
    @Autowired
    private JwtEncoder jwtEncoder;

    // Injicering av JwtDecoder för att avkoda JWT-token
    @Autowired
    private JwtDecoder jwtDecoder;

    // Metod för att generera ett JWT-token baserat på autentiseringsinformation
    public String generateJwt(Authentication auth){

        // Hämta aktuell tidpunkt
        Instant now = Instant.now();

        // Samla alla roller från autentiseringsinformationen till en space-separated sträng
        String scope = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        // Skapa JWT-anspråk (claims) med information som utfärdare, utfärdandedatum, ämne och roller
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .subject(auth.getName())
                .claim("roles", scope)
                .build();

        // Använd JwtEncoder för att koda anspråken och generera ett JWT-token
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}