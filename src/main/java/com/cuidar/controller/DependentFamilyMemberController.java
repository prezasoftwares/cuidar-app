package com.cuidar.controller;

import java.util.List;

import com.cuidar.dto.DependentMemberDTO;
import com.cuidar.model.DependentFamilyMember;
import com.cuidar.service.DependentFamilyMemberService;

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

    private DependentFamilyMemberService familymemberservice;
    
    public DependentFamilyMemberController(DependentFamilyMemberService familymemberservice) {
        this.familymemberservice = familymemberservice;
    }

    @GetMapping("")
    public List<DependentMemberDTO> get() {
        return this.familymemberservice.findAllDependents();
    }

    @GetMapping("/mainmember/{mainMemberId}")
    public Iterable<DependentFamilyMember> get(@PathVariable(name = "mainMemberId") Long mainMemberId){
        return this.familymemberservice.findAllDependentsMembersFromMain(mainMemberId);
    }

    @PostMapping("")
    public ResponseEntity<DependentFamilyMember> create(@RequestBody DependentMemberDTO dependentFamilyMember){
        return new ResponseEntity<>(this.familymemberservice.createDependentFamilyMember(dependentFamilyMember), HttpStatus.OK);
    }  

}