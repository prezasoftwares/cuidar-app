package com.cuidar.controller;

import java.util.UUID;

import javax.validation.Valid;

import com.cuidar.dto.MainFamilyMemberCreationDTO;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.service.CreateMainFamilyMemberService;
import com.cuidar.service.FindMainFamilyMemberService;

import org.modelmapper.ModelMapper;
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

    private CreateMainFamilyMemberService createMainFamilyMemberService;
    private FindMainFamilyMemberService findMainFamilyMemberService;
    private ModelMapper modelMapper;

    public MainFamilyMemberController(CreateMainFamilyMemberService createMainFamilyMemberService,
            FindMainFamilyMemberService findMainFamilyMemberService, ModelMapper modelMapper) {
        this.createMainFamilyMemberService = createMainFamilyMemberService;
        this.findMainFamilyMemberService = findMainFamilyMemberService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MainFamilyMember> get(@PathVariable(name = "id") UUID mainFamilyMemberId) {
        return new ResponseEntity<>(this.findMainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId),
                HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UUID> create(@Valid @RequestBody MainFamilyMemberCreationDTO mainFamilyMember) {
        MainFamilyMember newMainFamilyMember = mainFamilyMember.convertToEntity(this.modelMapper);
        return new ResponseEntity<>(this.createMainFamilyMemberService.createMainFamilyMember(newMainFamilyMember),
                HttpStatus.CREATED);
    }
}
