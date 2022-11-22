package com.digital.book.controller;

import com.digital.book.model.Book;
import com.digital.book.model.Reader;
import com.digital.book.pojo.BuyBookPojo;
import com.digital.book.repository.BookRepository;
import com.digital.book.repository.ReaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/reader")
public class ReaderController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReaderRepository readerRepository;

    @GetMapping("/books/search")
    public ResponseEntity<Object> getBooks(@RequestParam String title, @RequestParam String category,
                                         @RequestParam String authorName, @RequestParam Double price){

        List<Book> bookList = bookRepository.findByTitleAndCategoryAndAuthorNameAndPrice(title,category,authorName,price);
        if(bookList == null)
           return  ResponseEntity.status(HttpStatus.OK).body("");
        else {
            return ResponseEntity.status(HttpStatus.OK).body(bookList);
        }
    }

    @PostMapping("/books/buy")
    public ResponseEntity<Object> buyBook(@RequestBody BuyBookPojo buyBookPojo){
        Reader reader = Reader.builder()
                .bookId(buyBookPojo.getBookId())
                .email(buyBookPojo.getReader().getEmail())
                .name(buyBookPojo.getReader().getName())
                .purchaseTimestamp(System.currentTimeMillis())
                .isSubscribed(true)
                .build();
        readerRepository.save(reader);
        return ResponseEntity.status(HttpStatus.OK).body("Thank You for the Purchase");
    }

    @GetMapping("/{email_id}/books")
        public ResponseEntity<Object> getBookByEmail(@PathVariable ("email_id") String email){
         List<Reader> purchasedBook = readerRepository.findByEmail(email);
         return ResponseEntity.status(HttpStatus.OK).body(purchasedBook);
        }

    @GetMapping("/{email_id}/books/pid")
    public ResponseEntity<Object> getBookByPaymentId(@PathVariable ("email_id") String email,@RequestParam Integer paymentId){
        List<Reader> purchasedBook = readerRepository.findByPaymentId(paymentId);
        return ResponseEntity.status(HttpStatus.OK).body(purchasedBook);
    }

    @PutMapping("/{email_id}/books/{book_id}/cancel")
    public ResponseEntity<Object> cancelSubscription(@PathVariable ("email_id") String email,
                                                     @PathVariable ("book_id") Integer bookId) {
        Reader reader = readerRepository.findByBookIdAndEmail(bookId, email);
        long timeStamp = System.currentTimeMillis();
        long diff = timeStamp - reader.getPurchaseTimestamp() / (1000 * 60 * 60 );
        if(diff< 24) {
            reader.setSubscribed(false);
            readerRepository.save(reader);
            return ResponseEntity.status(HttpStatus.OK).body("Subscription cancelled successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Subscription cannot be cancelled");

    }

    @PutMapping("/{email_id}/books/{book_id}/read")
    public ResponseEntity<Object> readABook(@PathVariable ("email_id") String email, @PathVariable ("book_id") Integer bookId) {
        Reader reader = readerRepository.findByBookIdAndEmail(bookId,email);
        if(reader!=null)
        return ResponseEntity.status(HttpStatus.OK).body(reader);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Subscription cannot be cancelled");

    }
}
