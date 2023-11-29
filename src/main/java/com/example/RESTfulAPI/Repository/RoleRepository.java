package com.example.RESTfulAPI.Repository;


import com.example.RESTfulAPI.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Ett gränssnitt som fungerar som en repository för Role-entiteter
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    // En metod för att hitta en roll baserat på dess behörighet
    Optional<Role> findByAuthority(String authority);
}



