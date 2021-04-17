package com.cuidar.controller;

import java.util.UUID;

import com.cuidar.dto.MainMemberCreationDTO;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.service.MainFamilyMemberService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mainfamilymembers")
public class MainFamilyMemberController {

    private MainFamilyMemberService mainFamilyMemberService;

    public MainFamilyMemberController(MainFamilyMemberService mainFamilyMemberService) {
        this.mainFamilyMemberService = mainFamilyMemberService;
    }

    @GetMapping("")
    public Iterable<MainFamilyMember> get() {
        return this.mainFamilyMemberService.findAllMainFamilyMembers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MainFamilyMember> get(@PathVariable(name = "id") UUID mainFamilyMemberId){
        return new ResponseEntity<>(this.mainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MainFamilyMember> create(@RequestBody MainMemberCreationDTO mainFamilyMember){
        return new ResponseEntity<>(this.mainFamilyMemberService.createMainFamilyMember(mainFamilyMember), HttpStatus.CREATED);
    }
}
