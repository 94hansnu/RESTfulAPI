package com.example.RESTfulAPI.Service;

import com.example.RESTfulAPI.Entity.ApplicationUser;
import com.example.RESTfulAPI.Entity.LoginResponseDTO;
import com.example.RESTfulAPI.Entity.Role;
import com.example.RESTfulAPI.Repository.RoleRepository;
import com.example.RESTfulAPI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

    // olika injiceringar för olika funktioner

    // databasåtkomst för användare
    @Autowired
    private UserRepository userRepository;

    // databasåtkomst för roller
    @Autowired
    private RoleRepository roleRepository;

    // för att kryptera lösenord
    @Autowired
    private PasswordEncoder passwordEncoder;

    // AuthenticationManager för autentisering
    @Autowired
    private AuthenticationManager authenticationManager;

    // för hantering av JWT-token
    @Autowired
    private TokenService tokenService;

    // Metod för att registrera en ny användare
    public ApplicationUser registerUser(String username, String password){

        // Kryptera lösenordet
        String encodedPassword = passwordEncoder.encode(password);

        // Hämta användarrollen (USER) från databasen eller kasta ett undantag om den inte finns
        Role userRole = roleRepository.findByAuthority("USER").orElseThrow();

        // Skapa en uppsättning av roller för den nya användaren
        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        // Skapa och spara den nya användaren i databasen
        return userRepository.save(new ApplicationUser(0, username, encodedPassword, authorities));
    }

    // Metod för att logga in en användare och generera ett JWT-token
    public LoginResponseDTO loginUser(String username, String password){

        try{
            // Försök autentisera användaren med AuthenticationManager
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            // Generera ett JWT-token för den autentiserade användaren
            String token = tokenService.generateJwt(auth);

            // Hämta användaren från databasen och skapa en LoginResponseDTO med användaren och JWT-token
            return new LoginResponseDTO(userRepository.findByUsername(username).get(), token);

        } catch(AuthenticationException e){
            // Vid misslyckad autentisering, skapa en LoginResponseDTO med null-användare och ett tomt JWT-token
            return new LoginResponseDTO(null, "");
        }
    }

}

