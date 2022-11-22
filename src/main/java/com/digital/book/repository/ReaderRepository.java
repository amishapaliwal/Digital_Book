package com.digital.book.repository;

import com.digital.book.model.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReaderRepository extends JpaRepository<Reader,Integer> {
    public List<Reader> findByEmail(String email);
    public List<Reader> findByPaymentId(Integer paymentId);
    public Reader findByBookId(Integer bookId);
    public Reader findByBookIdAndEmail(Integer bookId, String emailId);
}
