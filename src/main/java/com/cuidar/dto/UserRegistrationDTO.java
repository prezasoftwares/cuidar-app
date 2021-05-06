package com.cuidar.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cuidar.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDTO extends DTOMapper<UserRegistrationDTO, User>{
    
    public UserRegistrationDTO(Class<UserRegistrationDTO> aType, Class<User> bType) {
        super(UserRegistrationDTO.class, User.class);
    }

    @NotNull
    @NotEmpty
    private String fullName;

    @NotNull
    @NotEmpty
    private String userName;
    
    @NotNull
    @NotEmpty
    private String password;
    
    @NotNull
    @NotEmpty
    private String email;
}