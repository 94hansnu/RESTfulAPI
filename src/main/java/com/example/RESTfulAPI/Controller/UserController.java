package com.example.RESTfulAPI.Controller;

import com.example.RESTfulAPI.Entity.ApplicationUser;
import com.example.RESTfulAPI.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    // En GET-metod som svarar på förfrågningar till "/user/"
    @GetMapping("/")
    public String helloUserController(){
        // Returnerar en enkel sträng som indikerar användaråtkomst
        return "User access level";
    }

    // En GET-metod för att hämta alla användare
    @GetMapping
    public String selectAllUsers(){
        // Hämtar alla användare från UserService
        List<ApplicationUser> users = userService.selectAllUsers();
        // Bygger ett svar med namnen på alla användare
        String response = "Namnen på alla users är: ";
        for (ApplicationUser user : users){
            response += user.getUsername() + ", ";
        }
        // Returnerar svar med namnen på alla användare
        return response;
    }

    // En GET-metod för att hämta en användare baserat på ID
    @GetMapping("/{id}")
    public String selectOneUserById(@PathVariable Long id){
        // Försök hämta användaren och få resultatet
        Optional<ApplicationUser> userOptional = userService.selectOneUserById(id);
        // Om användaren hittades, returnera information om användaren
        return userOptional.map(user -> String.format("Användaren %s har ID %d", user.getUsername(),user.getUserId())).orElse("Användaren hittades inte.");
    }

    // En POST-metod för att skapa en ny användare
    @PostMapping
    public String insertUser(@RequestBody ApplicationUser user){
        // Försök skapa användaren och få resultatet
        ApplicationUser insertedUser = userService.insertUser(user);
        // Returnera ett meddelande som bekräftar att användaren har skapats
        return String.format("Användaren %s skapad med ID %d", insertedUser.getUsername(), insertedUser.getUserId());
    }
}
