package com.example.RESTfulAPI.Controller;

import com.example.RESTfulAPI.Entity.ApplicationUser;
import com.example.RESTfulAPI.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String helloAdmineController(){
        return "Admin level access";
    }

    @PutMapping("/{id}")
    public String updateUser(@PathVariable Long id, @RequestBody ApplicationUser updatedUser){
        ApplicationUser updatedUserResult = userService.updateUser(id, updatedUser);
        return updatedUserResult != null ? "Användaren har uppdaterats." : "Användaren hittades inte.";
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return String.format("Användaren med ID %d har raderats.", id);
    }
}
