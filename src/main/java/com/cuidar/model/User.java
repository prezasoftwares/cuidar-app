package com.cuidar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "systemusers")
public class User extends BaseModel{
    
    @Column(unique = true)
    private String userName;
    private String password;

    private String fullName;
    @Column(unique = true)
    private String email;

    public User() {
        super();
    }

    public User(String username, String password, String fullName, String email) {
        this.userName = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
    }

}
