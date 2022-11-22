package com.digital.book.repository;

import com.digital.book.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {

    public Author findByEmailAndPassword(String email, String password);

}
