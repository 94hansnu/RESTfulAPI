package com.example.RESTfulAPI.Controller;

import com.example.RESTfulAPI.Entity.ApplicationUser;
import com.example.RESTfulAPI.Entity.Book;
import com.example.RESTfulAPI.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserService userService;

    private int tries = 0;

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




    // post medtod för att lägga till en bok
    @PostMapping("/book")
    public ResponseEntity<Book> addBook(Authentication authentication, @RequestBody Book book){
        Book newBook = userService.addBook(book, authentication.getName());
        return ResponseEntity.ok(newBook);
        // denna kmr alltid att fungera eftersom autentiseringen tar in namnet,
        // och namnet är sparat i DB därmed säkerställd lyckad hämtning av användare
    }

    //get metod för att hämta alla böcker
    @GetMapping("/book")
    public String getAllBooks(){
        List <Book> books = userService.gettAll();
        String allBooks = "LISTA PÅ ALLA BÖCKER:\n";
        for (Book book : books){
            allBooks += String.format("ID: %d\nTitel: %s\nFörfattare: %s\n",
                    book.getId(), book.getTitle(), book.getAuthor());
        } return allBooks;
    }

    // get metod för att hämta en specifik bok baserat på id
    @GetMapping("/book/{id}")
    public String getOne (@PathVariable Long id){
        if (tries > 3 ) System.exit(0);
        Optional <Book> book = userService.getOneById(id);
        if (book.isPresent()){
            return String.format("ID: %d\nTitel: %s\nFörfattare: %s\n",
                    book.get().getId(), book.get().getTitle(), book.get().getAuthor());
        }
        tries ++;
        return "Ingen bok med ID " + id + " finns registrerad i databasen.\n" +
                "Nu får du skärpa dig, fler felaktiga försök och jag kanske stänger av tjänsten >:O";
    }
}
