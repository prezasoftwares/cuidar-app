package com.cuidar.service;

import java.util.Set;
import java.util.UUID;

import com.cuidar.model.DependentFamilyMember;
import com.cuidar.model.MainFamilyMember;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class CreateFamilyService {
    CreateMainFamilyMemberService createMainFamilyMemberService;
    CreateDependentFamilyMemberService createDependentFamilyMemberService;

    public CreateFamilyService(CreateMainFamilyMemberService createMainFamilyMemberService, CreateDependentFamilyMemberService createDependentFamilyMemberService) {
        this.createMainFamilyMemberService = createMainFamilyMemberService;
        this.createDependentFamilyMemberService = createDependentFamilyMemberService;    
    }

    @Transactional
    public UUID createFamily(MainFamilyMember mainFamilyMember, Set<DependentFamilyMember> dependentFamilyMembers) {

        UUID createdMainFamilyMemberId = this.createMainFamilyMemberService.createMainFamilyMember(mainFamilyMember);

        for (DependentFamilyMember dependentFamilyMember : dependentFamilyMembers) {
            dependentFamilyMember.setMainFamilyMember(mainFamilyMember);
            this.createDependentFamilyMemberService.createDependentFamilyMember(dependentFamilyMember);
        }

        return createdMainFamilyMemberId;
    }
}
