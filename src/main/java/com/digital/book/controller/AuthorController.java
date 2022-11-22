package com.digital.book.controller;

import com.digital.book.model.Author;
import com.digital.book.model.Book;
import com.digital.book.pojo.AuthorPojo;
import com.digital.book.pojo.BookPojo;
import com.digital.book.repository.AuthorRepository;
import com.digital.book.repository.BookRepository;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Builder
@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/login")
    public String loginAuth(@RequestParam String email, @RequestParam String password){

        Author author = authorRepository.findByEmailAndPassword(email,password);
        if(author ==null){
            return "Login failed";
        }
        else {
            return "Login Successful";
        }
    }

    @GetMapping("/logout")
    public String logoutAuth(){
        return "Logout Successful";
    }

     @PostMapping("/signup")
    public ResponseEntity<Object> createAccount(@RequestBody AuthorPojo authorPojo){
        Author author = Author.builder()
                .authorName(authorPojo.getAuthorName())
                .email(authorPojo.getEmail())
                .password(authorPojo.getPassword())
                .build();

        authorRepository.save(author);
       return ResponseEntity.status(HttpStatus.OK).body("Account created successfully");
     }

     @PostMapping("/add/author/{authorId}")
    public ResponseEntity<Object> addBook(@RequestBody BookPojo bookPojo,@PathVariable Integer authorId){
        Book book = Book.builder()
                .authorId(authorId)
                .title(bookPojo.getTitle())
                .category(bookPojo.getCategory())
                .logo(bookPojo.getLogo())
                .price(bookPojo.getPrice())
                .publisher(bookPojo.getPublisher())
                .publishDate(bookPojo.getPublishDate())
                .isActive(bookPojo.isActive())
                .build();

            bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.OK).body("Book Added Successfully");
     }

     @PutMapping("/edit/book/{author_id}/{book_id}")
    public ResponseEntity<Object> editBook(@PathVariable("author_id") Integer authorId, @PathVariable("book_id") Integer bookId,@RequestBody BookPojo bookPojo){
       Optional<Book> book = bookRepository.findByAuthorIdAndBookId(authorId,bookId);
       if(book == null)
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book with book ID" +bookId +"doesn't exist");
       else{
        Book book1 = Book.builder()
                   .authorId(authorId)
                   .title(bookPojo.getTitle())
                   .category(bookPojo.getCategory())
                   .logo(bookPojo.getLogo())
                   .price(bookPojo.getPrice())
                   .publisher(bookPojo.getPublisher())
                   .publishDate(bookPojo.getPublishDate())
                   .isActive(bookPojo.isActive())
                   .build();
           bookRepository.save(book1);
           return ResponseEntity.status(HttpStatus.OK).body("Book Details Edited Successfully");
       }
    }

    @PostMapping("/status/{author_id}/{book_id}")
    public ResponseEntity<Object> changeStatus(@PathVariable Integer authorId,@PathVariable Integer bookId,
                                               @RequestParam boolean status){
        Optional<Book> book = bookRepository.findByAuthorIdAndBookId(authorId,bookId);
        if(!book.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book with book ID" +bookId +"doesn't exist");
        else{
            Book book1 = book.get();
            book1.setActive(status);
            bookRepository.save(book1);
        return ResponseEntity.status(HttpStatus.OK).body("Status changed successfully" );
    }
    }
}
