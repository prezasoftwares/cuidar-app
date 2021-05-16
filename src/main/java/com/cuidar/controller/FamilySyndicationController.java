package com.cuidar.controller;

import java.util.UUID;

import javax.validation.Valid;

import com.cuidar.dto.FamilySyndicationDTO;
import com.cuidar.service.SyndicationRegisterService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("syndication")
public class FamilySyndicationController {
    
    private SyndicationRegisterService syndicationRegisterService;

    public FamilySyndicationController(SyndicationRegisterService syndicationRegisterService) {
        this.syndicationRegisterService = syndicationRegisterService;
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<Object> approve(@PathVariable(name = "id") UUID mainFamilyMemberId, @Valid @RequestBody FamilySyndicationDTO familySyndicationDTO) {

        this.syndicationRegisterService.Approve(mainFamilyMemberId, familySyndicationDTO.getSyndicationNotes());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/reprove/{id}")
    public ResponseEntity<Object> reprove(@PathVariable(name = "id") UUID mainFamilyMemberId, @Valid @RequestBody FamilySyndicationDTO familySyndicationDTO){
        this.syndicationRegisterService.Reprove(mainFamilyMemberId, familySyndicationDTO.getSyndicationNotes());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
