package com.example.RESTfulAPI.Controller;

import com.example.RESTfulAPI.Entity.ApplicationUser;
import com.example.RESTfulAPI.Entity.Book;
import com.example.RESTfulAPI.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController {

    // Autowired används för att injicera en instans av UserService
    @Autowired
    private UserService userService;


    // En GET-metod som svarar på förfrågningar till "/admin/"
    @GetMapping("/")
    public String helloAdmineController(){
        return "Admin level access";
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody ApplicationUser updatedUser){
        try {
            // Anropar UserService för att uppdatera användaren och få resultatet
            ApplicationUser updatedUserResult = userService.updateUser(id, updatedUser);

            // Om uppdateringen lyckades, returnera framgångsmeddelande
            if (updatedUserResult != null) {
                return ResponseEntity.ok("Användaren har uppdaterats.");
            } else {
                // Om användaren inte hittades, returnera att användaren inte hittades
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Användaren hittades inte.");
            }
        } catch (Exception e) {
            // Om det uppstår ett fel, returnera felmeddelande error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        try {
            // Försök radera användaren och få resultatet
            boolean deleted = userService.deleteUser(id);

            // Om användaren raderades, returnera ett bekräftelsemeddelande
            if (deleted) {
                return ResponseEntity.ok(String.format("Användaren med ID %d har raderats.", id));
            } else {
                // Om användaren inte hittades, returnera meddelande
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Användaren med ID %d finns inte.", id));
            }
        } catch (Exception e) {
            // Om det uppstår ett fel, returnera felmeddelande
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    // delete metod för att radera en bok baserat på id
    @DeleteMapping("/book/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        Optional <Book> deleteBook = userService.getOneById(id);
        if (deleteBook.isPresent()){
            userService.deleteBook(id);
            return new ResponseEntity<>("Bok raderad", HttpStatus.OK);
        } return new ResponseEntity<>("Bok ej hittad", HttpStatus.BAD_REQUEST);
    }

    // put metod för att uppdatera författare på en befintlig bok baserat på id
    @PutMapping("/book/{id}")
    public ResponseEntity <Book> updateAuthor(@PathVariable Long id, @RequestBody String author){
        Optional <Book> updateBook = userService.getOneById(id);
        if (updateBook.isPresent()){
            updateBook.get().setAuthor(author);
            Book updated = userService.updateAuthor(updateBook.get());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else return ResponseEntity.notFound().build();
    }
}
