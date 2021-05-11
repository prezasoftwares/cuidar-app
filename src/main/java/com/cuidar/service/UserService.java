package com.cuidar.service;

import java.util.List;
import java.util.UUID;

import com.cuidar.exception.UserAlreadyRegisteredException;
import com.cuidar.exception.UserEmailAlreadyRegisteredException;
import com.cuidar.exception.UserRegisterSecretException;
import com.cuidar.model.User;
import com.cuidar.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public UUID registerNewUserAccount(User user, String userRegisterSecret) throws UserEmailAlreadyRegisteredException, 
                                                                                    UserRegisterSecretException,
                                                                                    UserAlreadyRegisteredException {
        
        String userRegisterSecretValue = System.getenv("cuidarUserRegisterSecret");

        if (userRegisterSecretValue == null){
            throw new UserRegisterSecretException("Chave secreta não configurada no ambiente");
        }else if (userRegisterSecret.equals("")){
            throw new UserRegisterSecretException("Chave secreta para registro de usuários não foi informada");
        }else if (!userRegisterSecret.equals(userRegisterSecretValue)){
            throw new UserRegisterSecretException("Chave secreta incorreta");
        }
        
        if (getByUserName(user.getUserName()) != null){
            throw new UserAlreadyRegisteredException("Já existe uma conta registrada com o identificador: " + user.getUserName());
        }

        if (emailExists(user.getEmail())) {
            throw new UserEmailAlreadyRegisteredException("Já existe uma conta registrada com o e-mail: " + user.getEmail());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepo.save(user).getId();
    }

    private boolean emailExists(String email) {
        return userRepo.findByEmail(email) != null;
    }

    public User getByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }
}
