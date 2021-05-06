package com.cuidar.controller;

import java.util.UUID;

import javax.validation.Valid;

import com.cuidar.dto.AuthInformationDTO;
import com.cuidar.dto.AuthRequestDTO;
import com.cuidar.dto.UserRegistrationDTO;
import com.cuidar.model.User;
import com.cuidar.service.UserService;
import com.cuidar.util.JwtUtils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authMgr;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<AuthInformationDTO> login(@RequestBody AuthRequestDTO request) {
        try {
            authMgr.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            String generatedJwtToken = jwtUtils.generateToken(request.getUsername());
            User foundUser = userService.getByUserName(request.getUsername());

            AuthInformationDTO authInformationDTO = new AuthInformationDTO(
                request.getUsername(), 
                foundUser.getFullName(),
                jwtUtils.extractExpiration(generatedJwtToken),
                generatedJwtToken);
            
            return new ResponseEntity<>(authInformationDTO, HttpStatus.OK);

        } catch (Exception ex) {
            System.out.println("error on login!");
            throw ex;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UUID> registerUserAccount(@Valid @RequestBody UserRegistrationDTO userRegistrationDTO) throws Exception {

        User newUser = userRegistrationDTO.convertToEntity(modelMapper);

        UUID generatedUserId = userService.registerNewUserAccount(newUser);

        return new ResponseEntity<>(generatedUserId, HttpStatus.OK);
    }
}
