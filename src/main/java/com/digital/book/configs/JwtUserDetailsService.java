package com.digital.book.configs;

import java.util.ArrayList;

import com.digital.book.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
            return new User(emailId,
                    authorRepository.findByEmail(emailId).getPassword(),
                    new ArrayList<>());

    }
}