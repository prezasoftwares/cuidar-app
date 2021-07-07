package com.cuidar.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.UUID;

import com.cuidar.dto.FamilyActionPlanCreationDTO;
import com.cuidar.exception.DomainValidationException;
import com.cuidar.exception.ResourceNotFoundException;
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

    public Iterable<FamilyActionPlanItem> findAllUndoneActionPlanItems(MainFamilyMember mainFamilyMember){
        return this.familyActionPlanItemRepo.findByMainFamilyMemberAndDone(mainFamilyMember, FamilyMemberNoYesFlag.No);
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

    public void updateActionPlanItem(UUID mainFamilyMemberId, UUID actionPlanItemId, FamilyMemberNoYesFlag done){

        FamilyActionPlanItem actionPlanItem = this.findFamilyActionPlanItem(actionPlanItemId);

        if (mainFamilyMemberId.toString().equals(actionPlanItem.getMainFamilyMemberId().toString())){
            actionPlanItem.setDone(done);
            this.familyActionPlanItemRepo.save(actionPlanItem);
        }
        else
        {
            DomainValidationException domainValidationException = new DomainValidationException("Dados inválidos");
            domainValidationException.addMessage("Membro principal inválido para o item do plano de ação informado");
            throw domainValidationException;
        }
    }

    public FamilyActionPlanItem findFamilyActionPlanItem(UUID actionPlanItemId){
        Optional<FamilyActionPlanItem> foundActionPlan = this.familyActionPlanItemRepo.findById(actionPlanItemId);

        if (!foundActionPlan.isPresent())
        {
            throw new ResourceNotFoundException("Item de plano de ação não encontrado", actionPlanItemId.toString());
        }

        FamilyActionPlanItem actionPlanItem = foundActionPlan.get();

        return actionPlanItem;
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
