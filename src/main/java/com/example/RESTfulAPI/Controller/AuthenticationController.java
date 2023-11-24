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

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body){
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }
}