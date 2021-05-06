package com.cuidar.repository;

import java.util.UUID;

import com.cuidar.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    public User findByEmail(String email);
    public User findByUserName(String userName);
}