package com.example.RESTfulAPI.Config;

import com.example.RESTfulAPI.Utils.RSAKeyProperties;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    //instans för att hålla RSA-nycklar
    private final RSAKeyProperties keys;

    //konstruktor för att injicera RSA-nycklar
    public SecurityConfiguration(RSAKeyProperties keys){
        this.keys = keys;
    }

    // Metod för att konfigurera en BCrypt-lösenordskrypterare som en Spring-bean
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    // Metod för att konfigurera en AuthenticationManager med användning av DaoAuthenticationProvider
    @Bean
    public AuthenticationManager authManager(UserDetailsService detailsService){
        // Skapa en DaoAuthenticationProvider
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        // Ange UserDetailsService och lösenordskrypterare för DaoAuthenticationProvider
        daoProvider.setUserDetailsService(detailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());
        // Returnera en ProviderManager med DaoAuthenticationProvider
        return new ProviderManager(daoProvider);
    }

    // Metod för att konfigurera en SecurityFilterChain för HTTP-säkerhetsregler
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                // Inaktivera CSRF-skydd
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    // Tillåter alla förfrågningar till /auth/**
                    auth.requestMatchers("/auth/**").permitAll();
                    // Kräver ADMIN-roll för förfrågningar till /admin/**
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    // Kräver antingen ADMIN- eller USER-roll
                    auth.requestMatchers("/user/**").hasAnyRole("ADMIN", "USER");
                    // Kräver autentisering för övriga förfrågningar
                    auth.anyRequest().authenticated();
                });

         // Konfigurerar OAuth2-resursserver och sessionhantering
        http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));
        http.sessionManagement(
                session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        // Returnera konfigurerad SecurityFilterChain
        return http.build();
    }
    // Metod för att skapa en JwtDecoder med hjälp av NimbusJwtDecoder och RSA-nyckel
    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withPublicKey(keys.getPublicKey()).build();
    }

    // Metod för att skapa en JwtEncoder med hjälp av NimbusJwtEncoder och RSA-nyckel
    @Bean
    public JwtEncoder jwtEncoder(){
        // Skapar en JWK och JWKSource med RSA-nycklar för kryptering
        JWK jwk = new RSAKey.Builder(keys.getPublicKey()).privateKey(keys.getPrivateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
        // Returnera en NimbusJwtEncoder med JWKSource
        return new NimbusJwtEncoder(jwks);
    }

    // Metod för att skapa en JwtAuthenticationConverter för att konvertera JWT till Authentication med rollinformation
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter(){
        // Skapa en JwtGrantedAuthoritiesConverter
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // Ange namnet på anspråket som innehåller rollinformation
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        // Ange prefix för rollerna
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        // Skapa en JwtAuthenticationConverter och sätt JwtGrantedAuthoritiesConverter
        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        // Returnera den konfigurerade JwtAuthenticationConverter
        return jwtConverter;
    }

}