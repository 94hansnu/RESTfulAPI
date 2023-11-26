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

    @GetMapping("/")
    public String helloUserController(){
        return "User access level";
    }

    @GetMapping
    public String selectAllUsers(){
        List<ApplicationUser> users = userService.selectAllUsers();
        String response = "Namnen på alla users är: ";
        for (ApplicationUser user : users){
            response += user.getUsername() + ", ";
        } return response;
    }

    @GetMapping("/{id}")
    public String selectOneUserById(@PathVariable Long id){
        Optional<ApplicationUser> userOptional = userService.selectOneUserById(id);
        return userOptional.map(user -> String.format("Användaren %s har ID %d", user.getUsername(),user.getUserId())).orElse("Användaren hittades inte.");
    }

    @PostMapping
    public String insertUser(@RequestBody ApplicationUser user){
        ApplicationUser insertedUser = userService.insertUser(user);
        return String.format("Användaren %s skapad med ID %d", insertedUser.getUsername(), insertedUser.getUserId());
    }
}
