package com.example.RESTfulAPI.Service;

import com.example.RESTfulAPI.Entity.ApplicationUser;
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

    public ApplicationUser updateUser(Long id, ApplicationUser updatedUser){

        Optional<ApplicationUser> existingUser = userRepository.findById(id);
        return existingUser.map(user -> {
            user.setUsername(updatedUser.getUsername());
            user.setPassword(updatedUser.getPassword());

            return userRepository.save(user);
        }).orElse(null);
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("In the user details service");

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }
}
