package com.digital.book.repository;

import com.digital.book.model.Author;
import com.digital.book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    public Optional<Book> findByAuthorIdAndBookId(Integer authorId, Integer bookId);
    public List<Book> findByTitleAndCategoryAndAuthorIdAndPrice(String title, String category,Integer authorId,Double price);
}
