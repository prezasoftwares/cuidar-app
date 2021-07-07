package com.cuidar.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import com.cuidar.dto.AttendanceRegisterTemplateInfoActioPlanItemDTO;
import com.cuidar.dto.AttendanceRegisterTemplateInfoDTO;
import com.cuidar.dto.AttendanceRegisterTemplateInfoFamilyMemberDTO;
import com.cuidar.model.DependentFamilyMember;
import com.cuidar.model.FamilyActionPlanItem;
import com.cuidar.model.FamilyAttendanceRecord;
import com.cuidar.model.FamilyAttendanceRecordLinkedMember;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberLinkType;
import com.cuidar.model.enums.FamilyMemberNoYesFlag;
import com.cuidar.repository.FamilyAttendanceRecordLinkedMemberRepo;
import com.cuidar.repository.FamilyAttendanceRecordRepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FamilyAttendanceService {

    private FindDependentFamilyMemberService findDependentFamilyMemberService;
    private FindMainFamilyMemberService findMainFamilyMemberService;
    private FamilyActionPlanService familyActionPlanService;
    private FamilyAttendanceRecordRepo familyAttendanceRecordRepo;
    private FamilyAttendanceRecordLinkedMemberRepo familyAttendanceRecordLinkedMemberRepo;
    private FamilyMemberResolverService familyMemberResolverService;

    public FamilyAttendanceService(FindMainFamilyMemberService findMainFamilyMemberService, FindDependentFamilyMemberService findDependentFamilyMemberService, FamilyActionPlanService familyActionPlanService, FamilyAttendanceRecordRepo familyAttendanceRecordRepo, FamilyAttendanceRecordLinkedMemberRepo familyAttendanceRecordLinkedMemberRepo, FamilyMemberResolverService familyMemberResolverService) {
        this.findMainFamilyMemberService = findMainFamilyMemberService;
        this.findDependentFamilyMemberService = findDependentFamilyMemberService;
        this.familyActionPlanService = familyActionPlanService;
        this.familyAttendanceRecordRepo = familyAttendanceRecordRepo;
        this.familyAttendanceRecordLinkedMemberRepo = familyAttendanceRecordLinkedMemberRepo;
        this.familyMemberResolverService = familyMemberResolverService;
    }

    @Transactional
    public void registerAttendanceRecord(UUID mainFamilyMemberId, FamilyAttendanceRecord familyAttendanceRecord, HashSet<UUID> doneActionPlanItemIds, HashSet<UUID> linkedFamilyMemberIds){
        
        MainFamilyMember foundMainFamilyMember = findMainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId);

        familyAttendanceRecord.setMainFamilyMember(foundMainFamilyMember);        

        doneActionPlanItemIds.forEach(item -> 
        {
            FamilyActionPlanItem familyActionPlanItem = familyActionPlanService.findFamilyActionPlanItem(item);

            if (familyAttendanceRecord.getSummaryActionPlanItems() == null){
                familyAttendanceRecord.setSummaryActionPlanItems(familyActionPlanItem.getDescription());
            }
            else{
                familyAttendanceRecord.setSummaryActionPlanItems(familyAttendanceRecord.getSummaryActionPlanItems() + ", " + familyActionPlanItem.getDescription());
            }
            
            familyActionPlanService.updateActionPlanItem(mainFamilyMemberId, item, FamilyMemberNoYesFlag.Yes);
        });

        familyAttendanceRecord.setAbscence(linkedFamilyMemberIds.size() == 0 ? FamilyMemberNoYesFlag.Yes : FamilyMemberNoYesFlag.No);

        FamilyAttendanceRecord savedAttendanceReport = this.familyAttendanceRecordRepo.save(familyAttendanceRecord);

        List<FamilyAttendanceRecordLinkedMember> list = new ArrayList<>();

        linkedFamilyMemberIds.forEach(item -> 
        {
            FamilyAttendanceRecordLinkedMember familyAttendanceRecordLinkedMember = new FamilyAttendanceRecordLinkedMember();
            familyAttendanceRecordLinkedMember.setAttendanceReport(savedAttendanceReport);
            familyAttendanceRecordLinkedMember.setReferencedFamilyMember(this.familyMemberResolverService.resolveFamilyMember(item));
            list.add(familyAttendanceRecordLinkedMember);
        });

        this.familyAttendanceRecordLinkedMemberRepo.saveAll(list);
    }

    public AttendanceRegisterTemplateInfoDTO generateFamilyAttendanceTemplate(UUID mainFamilyMemberId){
        AttendanceRegisterTemplateInfoDTO attendanceRecordTemplate = new  AttendanceRegisterTemplateInfoDTO();
        
        // family members
        MainFamilyMember foundMainFamilyMember = this.findMainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId);
        Iterable<DependentFamilyMember> foundDependentFamilyMembers = this.findDependentFamilyMemberService.findAllDependentsMembersFromMain(foundMainFamilyMember);

        AttendanceRegisterTemplateInfoFamilyMemberDTO familyMemberTemplateItem = new AttendanceRegisterTemplateInfoFamilyMemberDTO();
        familyMemberTemplateItem.setFullName(foundMainFamilyMember.getFullName());
        familyMemberTemplateItem.setFamilyLinkType(FamilyMemberLinkType.MainMember);
        familyMemberTemplateItem.setFamilyMemberId(foundMainFamilyMember.getId());

        attendanceRecordTemplate.getFamilyMembers().add(familyMemberTemplateItem);

        for (DependentFamilyMember dependentFamilyMember : foundDependentFamilyMembers) {
            familyMemberTemplateItem = new AttendanceRegisterTemplateInfoFamilyMemberDTO();
            familyMemberTemplateItem.setFullName(dependentFamilyMember.getFullName());
            familyMemberTemplateItem.setFamilyLinkType(dependentFamilyMember.getLinkTypeToMainMember());
            familyMemberTemplateItem.setFamilyMemberId(dependentFamilyMember.getId());

            attendanceRecordTemplate.getFamilyMembers().add(familyMemberTemplateItem);
        }

        // action plan items
        Iterable<FamilyActionPlanItem> familyActionPlanItems = this.familyActionPlanService.findAllUndoneActionPlanItems(foundMainFamilyMember);

        for (FamilyActionPlanItem familyActionPlanItem : familyActionPlanItems) {
            AttendanceRegisterTemplateInfoActioPlanItemDTO familyActionPlanItemTemplate = new AttendanceRegisterTemplateInfoActioPlanItemDTO();
            familyActionPlanItemTemplate.setActionPlanItemId(familyActionPlanItem.getId());
            familyActionPlanItemTemplate.setDescription(familyActionPlanItem.getDescription());
            familyActionPlanItemTemplate.setDueDate(familyActionPlanItem.getDueDate());
            familyActionPlanItemTemplate.setIsAssistentTask(familyActionPlanItem.getIsAssistentTask());
            familyActionPlanItemTemplate.setReferencedFamilyMember_Name(familyActionPlanItem.getReferencedFamilyMember_Name());

            attendanceRecordTemplate.getActionPlanItems().add(familyActionPlanItemTemplate);
        }

        return attendanceRecordTemplate;
    }

    public Page<FamilyAttendanceRecord> getAttendanceHistorByMainFamilyMember(UUID mainFamilyMemberId, int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        MainFamilyMember foundMainFamilyMember = this.findMainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId);

        Page<FamilyAttendanceRecord> pageAttendanceReport = this.familyAttendanceRecordRepo.findBymainFamilyMember(foundMainFamilyMember, paging);

        return pageAttendanceReport;
    }
}
