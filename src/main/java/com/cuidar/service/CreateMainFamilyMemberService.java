package com.cuidar.service;

import java.util.UUID;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberGeneralStatus;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class CreateMainFamilyMemberService {
    private MainFamilyMemberRepo mainFamilyMemberRepo;
    private ValidateMainFamilyMemberService validateMainFamilyMemberService;

    public CreateMainFamilyMemberService(MainFamilyMemberRepo mainFamilyMemberRepo, ValidateMainFamilyMemberService validateMainFamilyMemberService) {
        this.mainFamilyMemberRepo = mainFamilyMemberRepo;
        this.validateMainFamilyMemberService = validateMainFamilyMemberService;
    }

    public UUID createMainFamilyMember(MainFamilyMember mainFamilyMember) {
        validateMainFamilyMemberService.validate(mainFamilyMember);

        mainFamilyMember.setGeneralStatus(FamilyMemberGeneralStatus.PendingApproval);
        
        MainFamilyMember createdMainFamilyMember = this.mainFamilyMemberRepo.save(mainFamilyMember);     

        return createdMainFamilyMember.getId();
    }
}
