package com.example.RESTfulAPI.Controller;


import com.example.RESTfulAPI.Entity.ApplicationUser;
import com.example.RESTfulAPI.Entity.LoginResponseDTO;
import com.example.RESTfulAPI.Entity.RegistrationDTO;
import com.example.RESTfulAPI.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    // Autowired används för att injicera en instans av AuthenticationService
    @Autowired
    private AuthenticationService authenticationService;

    // En POST-metod för att registrera en ny användare
    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body){
        // Anropar AuthenticationService för att registrera en användare och returnerar resultatet
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }
    // En POST-metod för att logga in en användare
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        // Anropar AuthenticationService för att logga in en användare och returnerar resultatet
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}