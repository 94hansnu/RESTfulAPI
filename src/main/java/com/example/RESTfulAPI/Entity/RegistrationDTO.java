package com.example.RESTfulAPI.Entity;

// DTO (Data Transfer Object) som representerar användarinformation vid registrering
public class RegistrationDTO {
    //Användarnamn för registrering
    private String username;

    //Lösenord för registrering
    private String password;

    public RegistrationDTO(){
        super();
    }

    //konstruktor
    public RegistrationDTO(String username, String password){
        super();
        this.username = username;
        this.password = password;
    }

    //getter och setter
    public String getUsername(){
        return this.username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    //  toString-metod för att skapa en strängrepresentation av RegistrationDTO
    public String toString(){
        return "Registration info: username: " + this.username + " password: " + this.password;
    }
}
