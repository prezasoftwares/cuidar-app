package com.cuidar.service;

import java.util.Calendar;
import java.util.UUID;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberGeneralStatus;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class SyndicationRegisterService {
    private FindMainFamilyMemberService findMainFamilyMemberService;
    private FamilyStatusUpdateService familyStatusUpdateService;
    private MainFamilyMemberRepo mainFamilyMemberRepo;

    public SyndicationRegisterService(FindMainFamilyMemberService findMainFamilyMemberService, FamilyStatusUpdateService familyStatusUpdateService, MainFamilyMemberRepo mainFamilyMemberRepo) {
        this.findMainFamilyMemberService = findMainFamilyMemberService;
        this.familyStatusUpdateService = familyStatusUpdateService;
        this.mainFamilyMemberRepo = mainFamilyMemberRepo;
    }

    @Transactional
    public void Approve(UUID mainFamilyMemberId, String syndicationNotes){
        MainFamilyMember foundMainFamilyMember = findMainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId);

        familyStatusUpdateService.validateStatusTransition(foundMainFamilyMember.getGeneralStatus(), FamilyMemberGeneralStatus.Active);

        Calendar assistenceDueDateCalendar = Calendar.getInstance();
        assistenceDueDateCalendar.add(Calendar.MONTH, 6);
        
        foundMainFamilyMember.setGeneralStatus(FamilyMemberGeneralStatus.Active);
        foundMainFamilyMember.setAssistenceDueDate(assistenceDueDateCalendar.getTime());

        mainFamilyMemberRepo.save(foundMainFamilyMember);

        this.familyStatusUpdateService.registerStatusUpdate(foundMainFamilyMember, syndicationNotes);
    }

    @Transactional
    public void Reprove(UUID mainFamilyMemberId, String syndicationNotes) {
        MainFamilyMember foundMainFamilyMember = findMainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId);

        familyStatusUpdateService.validateStatusTransition(foundMainFamilyMember.getGeneralStatus(), FamilyMemberGeneralStatus.Reproved);

        foundMainFamilyMember.setGeneralStatus(FamilyMemberGeneralStatus.Reproved);
    
        mainFamilyMemberRepo.save(foundMainFamilyMember);

        this.familyStatusUpdateService.registerStatusUpdate(foundMainFamilyMember, syndicationNotes);
    }

    public void Promote(UUID mainFamilyMemberId, String syndicationNotes) {
        MainFamilyMember foundMainFamilyMember = findMainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId);

        familyStatusUpdateService.validateStatusTransition(foundMainFamilyMember.getGeneralStatus(), FamilyMemberGeneralStatus.Promoted);

        foundMainFamilyMember.setGeneralStatus(FamilyMemberGeneralStatus.Promoted);
    
        mainFamilyMemberRepo.save(foundMainFamilyMember);

        this.familyStatusUpdateService.registerStatusUpdate(foundMainFamilyMember, syndicationNotes);
    }

    public void Suspend(UUID mainFamilyMemberId, String syndicationNotes) {
        MainFamilyMember foundMainFamilyMember = findMainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId);

        familyStatusUpdateService.validateStatusTransition(foundMainFamilyMember.getGeneralStatus(), FamilyMemberGeneralStatus.Suspended);

        foundMainFamilyMember.setGeneralStatus(FamilyMemberGeneralStatus.Suspended);
    
        mainFamilyMemberRepo.save(foundMainFamilyMember);

        this.familyStatusUpdateService.registerStatusUpdate(foundMainFamilyMember, syndicationNotes);
    }
}
