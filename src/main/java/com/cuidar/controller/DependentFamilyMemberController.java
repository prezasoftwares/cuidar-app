package com.cuidar.controller;

import java.util.List;
import java.util.UUID;

import com.cuidar.dto.DependentMemberDTO;
import com.cuidar.model.DependentFamilyMember;
import com.cuidar.service.DependentFamilyMemberService;
import com.cuidar.service.FindDependentFamilyMemberService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dependentfamilymembers")
public class DependentFamilyMemberController {

    private FindDependentFamilyMemberService findDependentMemberService;

    public DependentFamilyMemberController(FindDependentFamilyMemberService findDependentMemberService) {
        this.findDependentMemberService = findDependentMemberService;
    }
/*
    @GetMapping("/{id}")
    public DependentMemberDTO get(@PathVariable(name = "id") UUID dependentFamilyMemberId) {
        return this.findDependentMemberService.findDependentFamilyMemberById(dependentFamilyMemberId);
    }

    @GetMapping("/mainmember/{mainMemberId}")
    public Iterable<DependentFamilyMember> get(@PathVariable(name = "mainMemberId") UUID mainMemberId) {
        return this.familymemberservice.findAllDependentsMembersFromMain(mainMemberId);
    }

    @PostMapping("")
    public ResponseEntity<DependentFamilyMember> create(@RequestBody DependentMemberDTO dependentFamilyMember) {
        return new ResponseEntity<>(this.familymemberservice.createDependentFamilyMember(dependentFamilyMember),
                HttpStatus.OK);
    }
    */

}