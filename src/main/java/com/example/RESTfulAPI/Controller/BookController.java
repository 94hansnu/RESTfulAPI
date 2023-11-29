package com.example.RESTfulAPI.Controller;

import com.example.RESTfulAPI.Entity.Book;
import com.example.RESTfulAPI.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private UserService userService;

    private int tries = 0;

    // post medtod för att lägga till en bok
    @PostMapping("")
    public ResponseEntity<Book> addBook(Authentication authentication, @RequestBody Book book){
        Book newBook = userService.addBook(book, authentication.getName());
        return ResponseEntity.ok(newBook);
        // denna kmr alltid att fungera eftersom autentiseringen tar in namnet,
        // och namnet är sparat i DB därmed säkerställd lyckad hämtning av användare
    }

    // delete metod för att radera en bok baserat på id
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        Optional <Book> deleteBook = userService.getOneById(id);
        if (deleteBook.isPresent()){
            userService.deleteBook(id);
            return new ResponseEntity<>("Bok raderad", HttpStatus.OK);
        } return new ResponseEntity<>("Bok ej hittad", HttpStatus.BAD_REQUEST);
    }

    //get metod för att hämta alla böcker
    @GetMapping("")
    public String getAllBooks(){
        List <Book> books = userService.gettAll();
        String allBooks = "LISTA PÅ ALLA BÖCKER:\n";
        for (Book book : books){
            allBooks += String.format("ID: %d\nTitel: %s\nFörfattare: %s\n",
                    book.getId(), book.getTitle(), book.getAuthor());
        } return allBooks;
    }

    // get metod för att hämta en specifik bok baserat på id
    @GetMapping("/{id}")
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

    // put metod för att uppdatera författare på en befintlig bok baserat på id
    @PutMapping("/{id}")
    public ResponseEntity <Book> updateAuthor(@PathVariable Long id, @RequestBody String author){
        Optional <Book> updateBook = userService.getOneById(id);
        if (updateBook.isPresent()){
            updateBook.get().setAuthor(author);
            Book updated = userService.updateAuthor(updateBook.get());
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else return ResponseEntity.notFound().build();
    }
}
