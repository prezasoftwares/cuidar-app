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
    private FamilyStatusUpdateService familyStatusUpdateService;

    public CreateMainFamilyMemberService(MainFamilyMemberRepo mainFamilyMemberRepo, ValidateMainFamilyMemberService validateMainFamilyMemberService, FamilyStatusUpdateService familyStatusUpdateService) {
        this.mainFamilyMemberRepo = mainFamilyMemberRepo;
        this.validateMainFamilyMemberService = validateMainFamilyMemberService;
        this.familyStatusUpdateService = familyStatusUpdateService;
    }

    public UUID createMainFamilyMember(MainFamilyMember mainFamilyMember) {
        //validates business rules
        validateMainFamilyMemberService.validate(mainFamilyMember);

        //set the default general status
        mainFamilyMember.setGeneralStatus(FamilyMemberGeneralStatus.PendingApproval);
        
        //persists mainFamilyMember
        MainFamilyMember createdMainFamilyMember = this.mainFamilyMemberRepo.save(mainFamilyMember);     

        //save status update record
        this.familyStatusUpdateService.registerStatusUpdate(mainFamilyMember, FamilyStatusUpdateService.getDefaultStatusTextFor(mainFamilyMember.getGeneralStatus()));

        return createdMainFamilyMember.getId();
    }
}
