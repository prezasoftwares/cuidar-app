package com.cuidar.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

import com.cuidar.dto.FamilyActionPlanCreationDTO;
import com.cuidar.exception.DomainValidationException;
import com.cuidar.model.DependentFamilyMember;
import com.cuidar.model.FamilyActionPlanItem;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberGeneralStatus;
import com.cuidar.model.enums.FamilyMemberNoYesFlag;
import com.cuidar.repository.FamilyActionPlanItemRepo;

import org.springframework.stereotype.Service;

@Service
public class FamilyActionPlanService {

    private FamilyActionPlanItemRepo familyActionPlanItemRepo;
    private FindMainFamilyMemberService findMainFamilyMemberService;
    private FindDependentFamilyMemberService findDependentFamilyMemberService;

    public FamilyActionPlanService(FamilyActionPlanItemRepo familyActionPlanItemRepo, FindMainFamilyMemberService findMainFamilyMemberService, FindDependentFamilyMemberService findDependentFamilyMemberService) {
        this.familyActionPlanItemRepo = familyActionPlanItemRepo;
        this.findMainFamilyMemberService = findMainFamilyMemberService;
        this.findDependentFamilyMemberService = findDependentFamilyMemberService;
    }

    public Iterable<FamilyActionPlanItem> findAllActionPlanItems(MainFamilyMember mainFamilyMember) {
        return this.familyActionPlanItemRepo.findBymainFamilyMember(mainFamilyMember);
    }
    
    public void createItemInActionPlan(UUID mainFamilyMemberId, FamilyActionPlanCreationDTO familyActionPlanItemDTO){
        
        FamilyActionPlanItem familyActionPlanItem = new FamilyActionPlanItem();

        familyActionPlanItem.setDescription(familyActionPlanItemDTO.getDescription());
        familyActionPlanItem.setDone(FamilyMemberNoYesFlag.No);
        familyActionPlanItem.setDueDate(familyActionPlanItemDTO.getDueDate());
        familyActionPlanItem.setIsAssistentTask(familyActionPlanItemDTO.getIsAssistentTask());
        
        MainFamilyMember mainFamilyMember = this.findMainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId);
        DependentFamilyMember dependentFamilyMember = new DependentFamilyMember();

        familyActionPlanItem.setMainFamilyMember(mainFamilyMember);

        if (familyActionPlanItemDTO.getReferencedFamilyMemberId() != null){
            if (!familyActionPlanItemDTO.getReferencedFamilyMemberId().toString().equals(mainFamilyMemberId.toString())){
                dependentFamilyMember = this.findDependentFamilyMemberService.findDependentFamilyMemberById(familyActionPlanItemDTO.getReferencedFamilyMemberId());
                familyActionPlanItem.setReferencedFamilyMember(dependentFamilyMember);
            }
            else if (familyActionPlanItemDTO.getReferencedFamilyMemberId().toString().equals(mainFamilyMemberId.toString())){
                familyActionPlanItem.setReferencedFamilyMember(mainFamilyMember);
            }
        }

        this.validateActionPlanItem(mainFamilyMember, dependentFamilyMember, familyActionPlanItem);

        this.familyActionPlanItemRepo.save(familyActionPlanItem);
    }

    private void validateActionPlanItem(MainFamilyMember mainFamilyMember, DependentFamilyMember dependentFamilyMember, FamilyActionPlanItem familyActionPlanItem){
        DomainValidationException domainValidationException = new DomainValidationException("Erro ao criar item no plano de ação");

        if (mainFamilyMember.getGeneralStatus() != FamilyMemberGeneralStatus.Active){
            domainValidationException.addMessage("O status da família não permite modificação no plano de ação");
        }
        else if (familyActionPlanItem.getDueDate() != null && familyActionPlanItem.getDueDate().after(mainFamilyMember.getAssistenceDueDate())){
            DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
            domainValidationException.addMessage("Não é possível ter uma ação com a data após " + dateFormatter.format(mainFamilyMember.getAssistenceDueDate()));
        }
        else if (dependentFamilyMember.getMainFamilyMember() != null && dependentFamilyMember.getMainFamilyMember_Id() != mainFamilyMember.getId()){
            domainValidationException.addMessage("Membro referenciado na ação não está associado ao membro principal");
        }
        
        if (!domainValidationException.getValidations().isEmpty()){
            throw domainValidationException;
        }
    }
}
