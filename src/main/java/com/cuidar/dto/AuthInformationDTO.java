package com.cuidar.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class AuthInformationDTO {
    private String userName;
    private String fullName;
    private Date expirationDate;
    private String token;
}
