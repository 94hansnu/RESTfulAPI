package com.example.RESTfulAPI.Repository;

import com.example.RESTfulAPI.Entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Ett gränssnitt som fungerar som en repository för ApplicationUser-entiteter
@Repository
public interface UserRepository extends JpaRepository<ApplicationUser, Long> {
    // En metod för att hitta en användare baserat på användarnamn
    Optional<ApplicationUser> findByUsername(String username);
}


