package com.cuidar.service;

import java.util.HashSet;
import java.util.UUID;

import com.cuidar.exception.DomainValidationException;
import com.cuidar.model.FamilyStatusUpdateRecord;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberGeneralStatus;
import com.cuidar.repository.FamilyStatusUpdateRecordRepo;

import org.springframework.stereotype.Service;

@Service
public class FamilyStatusUpdateService {
    
    private FamilyStatusUpdateRecordRepo familyStatusUpdateRecordRepo;

    public FamilyStatusUpdateService(FamilyStatusUpdateRecordRepo familyStatusUpdateRecordRepo) {
        this.familyStatusUpdateRecordRepo = familyStatusUpdateRecordRepo;
    }

    public UUID registerStatusUpdate(MainFamilyMember mainFamilyMember, String statusUpdateDescription){
        FamilyStatusUpdateRecord statusUpdateRecord = new FamilyStatusUpdateRecord(mainFamilyMember, statusUpdateDescription, mainFamilyMember.getGeneralStatus());

        statusUpdateRecord = this.familyStatusUpdateRecordRepo.save(statusUpdateRecord);

        return statusUpdateRecord.getId();
    }

    public void validateStatusTransition(FamilyMemberGeneralStatus fromFamilyMemberGeneralStatus, FamilyMemberGeneralStatus toFamilyMemberGeneralStatus){
        HashSet<FamilyMemberGeneralStatus> validStatus = new HashSet<>();

        switch (fromFamilyMemberGeneralStatus) {
            case PendingApproval:
                validStatus.add(FamilyMemberGeneralStatus.Active);
                validStatus.add(FamilyMemberGeneralStatus.Reproved);
                break;
            case Active:
                validStatus.add(FamilyMemberGeneralStatus.Promoted);
                validStatus.add(FamilyMemberGeneralStatus.Suspended);
                break;
            case Promoted:
            case Suspended:
            case Reproved:
                break;
            default:
                throw new IllegalArgumentException("");
        }
        
        if (!validStatus.contains(toFamilyMemberGeneralStatus))
        {
            DomainValidationException validationException = new DomainValidationException("Condição inválida para alteração de status");
            validationException.addMessage("Status não pode ser alterado de '" + 
                                            FamilyStatusUpdateService.getDefaultStatusTextFor(fromFamilyMemberGeneralStatus) + 
                                            "' para '" +
                                            FamilyStatusUpdateService.getDefaultStatusTextFor(toFamilyMemberGeneralStatus) +
                                            "'");

            throw validationException;
        }
    }

    public static String getDefaultStatusTextFor(FamilyMemberGeneralStatus familyMemberGeneralStatus) throws IllegalArgumentException{
        switch (familyMemberGeneralStatus) {
            case Active:
                return "Ativo";
            case PendingApproval:
                return "Para aprovação";
            case Promoted:
                return "Promovido";
            case Suspended:
                return "Suspenso";
            case Reproved:
                return "Rejeitado";
            default:
                throw new IllegalArgumentException();
        }
    }
}
