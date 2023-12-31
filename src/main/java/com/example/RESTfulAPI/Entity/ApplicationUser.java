package com.example.RESTfulAPI.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "users")
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    private Integer userId;

    // Unik kolumn för användarnamn
    @Column(unique=true)
    private String username;

    // Lösenord för användaren
    private String password;

    // Många-till-många-relation med Roll (Role)
    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(
            name="user_role_junction",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    private Set<Role> authorities;

    // Standardkonstruktor som initierar en tom uppsättning av roller
    public ApplicationUser() {
        super();
        authorities = new HashSet<>();
    }

    // Konstruktor med parametrar för att skapa en användare med specifika egenskaper
    public ApplicationUser(Integer userId, String username, String password, Set<Role> authorities) {
        super();
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    //Getter och setter
    public Integer getUserId() {
        return this.userId;
    }

    public void setId(Integer userId) {
        this.userId = userId;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    // Metod från UserDetails för att hämta användarens roller
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
    // Metod från UserDetails för att hämta användarens lösenord
    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Metod från UserDetails för att hämta användarens användarnamn
    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    // Följande fyra metoder från UserDetails används för att hantera användarens konto-status
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}