package com.digital.book.controller;

import com.digital.book.entity.JwtResponse;
import com.digital.book.model.Author;
import com.digital.book.model.Book;
import com.digital.book.pojo.AuthorPojo;
import com.digital.book.pojo.BookPojo;
import com.digital.book.repository.AuthorRepository;
import com.digital.book.repository.BookRepository;
import com.digital.book.service.UserService;
import com.digital.book.utility.JWTUtility;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> createAccount(@RequestBody AuthorPojo authorPojo) {
        Author author = Author.builder()
                .authorName(authorPojo.getAuthorName())
                .email(authorPojo.getEmail())
                .password(authorPojo.getPassword())
                .build();

        authorRepository.save(author);
        return ResponseEntity.status(HttpStatus.OK).body("Account created successfully");
    }

    @GetMapping("/login")
    public ResponseEntity loginAuth(@RequestParam String email, @RequestParam String password)  {
        Author author = authorRepository.findByEmailAndPassword(email, password);
        if (author == null)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Login failed");

        final UserDetails userDetails = userService.loadUserByUsername(email);

        final String token = jwtUtility.generateToken(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new JwtResponse(token));
    }

    @GetMapping("/logout")
    public String logoutAuth() {
        return "Logout Successful";
    }

    @PostMapping("/add/author/{authorId}")
    public ResponseEntity<Object> addBook(@RequestBody BookPojo bookPojo, @PathVariable Integer authorId) {
        if (authorRepository.findById(authorId).isPresent()) {
            Book book = Book.builder()
                    .authorId(authorId)
                    .title(bookPojo.getTitle())
                    .category(bookPojo.getCategory())
                    .logo(bookPojo.getLogo())
                    .price(bookPojo.getPrice())
                    .publisher(bookPojo.getPublisher())
                    .publishDate(System.currentTimeMillis())
                    .isActive(bookPojo.getIsActive())
                    .build();

            bookRepository.save(book);
            return ResponseEntity.status(HttpStatus.OK).body("Book Added Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Author does not exist");

        }
    }

    @PutMapping("/edit/book/{author_id}/{book_id}")
    public ResponseEntity<Object> editBook(@PathVariable("author_id") Integer authorId, @PathVariable("book_id") Integer bookId, @RequestBody BookPojo bookPojo) {
        Optional<Book> book = bookRepository.findByAuthorIdAndBookId(authorId, bookId);
        if (!book.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book with book ID" + bookId + "doesn't exist");
        else {
            Book book1 = book.get();
            book1.setAuthorId(authorId);
            book1.setTitle(bookPojo.getTitle() != null ? bookPojo.getTitle() : book1.getTitle());
            book1.setLogo(bookPojo.getLogo() != null ? bookPojo.getLogo() : book1.getLogo());
            book1.setCategory(bookPojo.getCategory() != null ? bookPojo.getCategory() : book1.getCategory());
            book1.setPrice(bookPojo.getPrice() != null ? bookPojo.getPrice() : book1.getPrice());
            book1.setPublisher(bookPojo.getPublisher() != null ? bookPojo.getPublisher() : book1.getPublisher());
            bookRepository.save(book1);
            return ResponseEntity.status(HttpStatus.OK).body("Book Details Edited Successfully");
        }
    }

    @PostMapping("/status/{author_id}/{book_id}")
    public ResponseEntity<Object> changeStatus(@PathVariable("author_id") Integer authorId, @PathVariable("book_id") Integer bookId,
                                               @RequestParam boolean status) {
        Optional<Book> book = bookRepository.findByAuthorIdAndBookId(authorId, bookId);
        if (!book.isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book with book ID" + bookId + "doesn't exist");
        else {
            Book book1 = book.get();
            book1.setActive(status);
            bookRepository.save(book1);
            return ResponseEntity.status(HttpStatus.OK).body("Status changed successfully");
        }
    }
}
