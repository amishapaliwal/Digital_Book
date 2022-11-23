package com.digital.book.service;

import java.util.ArrayList;

import com.digital.book.model.Author;
import com.digital.book.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // logic to get the user form the db
        Author author = authorRepository.findByEmail(email);
        return new User(email, author.getPassword(), new ArrayList<>());
    }

}
