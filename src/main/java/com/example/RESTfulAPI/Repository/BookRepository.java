package com.example.RESTfulAPI.Repository;

import com.example.RESTfulAPI.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book> {
}
