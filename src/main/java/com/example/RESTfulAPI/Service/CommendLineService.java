package com.example.RESTfulAPI.Service;

import com.example.RESTfulAPI.Repository.BookRepository;
import com.example.RESTfulAPI.Repository.RoleRepository;
import com.example.RESTfulAPI.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CommendLineService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final BookRepository bookRepository;

    public CommendLineService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, BookRepository bookRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.bookRepository = bookRepository;
    }
}
