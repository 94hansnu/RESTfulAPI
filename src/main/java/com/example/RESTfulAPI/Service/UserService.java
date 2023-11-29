package com.example.RESTfulAPI.Service;

import com.example.RESTfulAPI.Entity.ApplicationUser;
import com.example.RESTfulAPI.Entity.Book;
import com.example.RESTfulAPI.Repository.BookRepository;
import com.example.RESTfulAPI.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;


    // hämta alla User
    public List<ApplicationUser> selectAllUsers (){
        return userRepository.findAll();
    }

    // hämta User med specifikt id
    public Optional<ApplicationUser> selectOneUserById(Long id) {
        return userRepository.findById(id);
    }

    // create/skapa ny user
    public ApplicationUser insertUser(ApplicationUser user){
        return userRepository.save(user);
    }

    // uppdatera användare baserat på id
    public ApplicationUser updateUser(Long id, ApplicationUser updatedUser){

        // Hämta den befintliga användaren från databasen baserat på ID
        Optional<ApplicationUser> existingUser = userRepository.findById(id);

        // Om användaren finns, uppdatera dess attribut och spara i databasen
        return existingUser.map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());

            return userRepository.save(user);
        }).orElse(null); // Returnera null om användaren inte finns
    }


    // En metod för att radera en användare baserat på ID
    public boolean deleteUser(Long id){

        // Kontrollera om användaren med det angivna ID:t finns i databasen
        Optional<ApplicationUser> userOptional = userRepository.findById(id);

        // Om användaren finns, radera den och returnera true
        if (userOptional.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            // Om användaren inte finns, returnera false
            return false;
        }
    }

    // Lägg till en bok kopplad till en användare
    public Book addBook(Book book, String username){
        // hämta användaren baserat på användarnamn
        Optional <ApplicationUser> user = userRepository.findByUsername(username);
        // om användaren finns, koppla boken till användaren och spara i db
        if (user.isPresent()){
            book.setUser(user.get());
            return bookRepository.save(book);
        }
        // om användaren inte finns returnera null
        else return null;
    }

    // raderar en bok baserat på id
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

    // hämtar en bok baserat på id
    public Optional <Book> getOneById(Long id){
        return bookRepository.findById(id);
    }

    // hämtar alla böcker
    public List<Book> gettAll(){
        return bookRepository.findAll();
    }

    // uppdaterar en författare på en befintlig bok
    public Book updateAuthor(Book book){
        return bookRepository.save(book);
    }
    // Överskuggad metod från UserDetailsService för att hämta användaruppgifter baserat på användarnamn
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("In the user details service");

        // Sök efter användaren i databasen baserat på användarnamn, Kasta undantag om användaren inte hittas
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }
}
