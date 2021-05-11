package com.cuidar.service;

import java.util.ArrayList;
import java.util.Optional;

import com.cuidar.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    
    @Autowired
    private UserService usersSvc;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = usersSvc.getAll()
            .stream()
            .filter(x -> x.getUserName().equalsIgnoreCase(username))
            .findFirst();

        if (user.isEmpty()){
            throw new UsernameNotFoundException("Usuário não encontrado");
        } 

        return new org.springframework.security.core.userdetails.User(user.get().getUserName(), user.get().getPassword(), new ArrayList<>());
    }
    
}
