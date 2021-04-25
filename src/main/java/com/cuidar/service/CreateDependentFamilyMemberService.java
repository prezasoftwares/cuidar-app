package com.cuidar.service;

import java.util.UUID;

import com.cuidar.model.DependentFamilyMember;
import com.cuidar.repository.DependentFamilyMemberRepo;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class CreateDependentFamilyMemberService {

    private DependentFamilyMemberRepo dependentFamilyMemberRepo;
    private ValidateDependentFamilyMemberService validateDependentFamilyMemberService;

    public CreateDependentFamilyMemberService(DependentFamilyMemberRepo dependentFamilyMemberRepo, ValidateDependentFamilyMemberService validateDependentFamilyMemberService) {
        this.dependentFamilyMemberRepo = dependentFamilyMemberRepo;
        this.validateDependentFamilyMemberService = validateDependentFamilyMemberService;
    }
    
    public UUID createDependentFamilyMember(DependentFamilyMember dependentFamilyMember) {
        
        this.validateDependentFamilyMemberService.validate(dependentFamilyMember);

        DependentFamilyMember createdDependentFamilyMember = this.dependentFamilyMemberRepo.save(dependentFamilyMember);

        return createdDependentFamilyMember.getId();
    }
}
