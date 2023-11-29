package com.example.RESTfulAPI.Entity;

// Enkel DTO (Data Transfer Object) som representerar responsen för inloggningen
public class LoginResponseDTO {

    // Användaren som är inloggad
    private ApplicationUser user;

    // JWT-token för autentisering och auktorisering
    private String jwt;

    public LoginResponseDTO(){
        super();
    }

    // Konstruktor med parametrar för att skapa en LoginResponseDTO med specifika egenskaper
    public LoginResponseDTO(ApplicationUser user, String jwt){
        this.user = user;
        this.jwt = jwt;
    }

    //Getter och setter
    public ApplicationUser getUser(){
        return this.user;
    }

    public void setUser(ApplicationUser user){
        this.user = user;
    }

    public String getJwt(){
        return this.jwt;
    }

    public void setJwt(String jwt){
        this.jwt = jwt;
    }
}
