package com.cuidar.service;

import java.util.Calendar;

import com.cuidar.exception.DomainValidationException;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberHousingType;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.springframework.stereotype.Service;

@Service
public class ValidateMainFamilyMemberService {
    private MainFamilyMemberRepo mainFamilyMemberRepo;

    public ValidateMainFamilyMemberService(MainFamilyMemberRepo mainFamilyMemberRepo) {
        this.mainFamilyMemberRepo = mainFamilyMemberRepo;    
    }

    public void setMainFamilyMemberRepo(MainFamilyMemberRepo mainFamilyMemberRepo){
        this.mainFamilyMemberRepo = mainFamilyMemberRepo;
    }

    public void validate(MainFamilyMember mainFamilyMember) {
        DomainValidationException validationExc = new DomainValidationException("Validação dos dados do membro principal");

        if (this.mainFamilyMemberRepo.existFamilyMemberByDocumentId(mainFamilyMember.getDocumentId())){
            validationExc.addMessage("O número de documento informado já foi utilizado em outro cadastro");
        }

        if (mainFamilyMember.getHousingType() != null
        && mainFamilyMember.getHousingType() == FamilyMemberHousingType.Other
        && (mainFamilyMember.getHousingTypeNotes() == null || mainFamilyMember.getHousingTypeNotes().isBlank())){
            validationExc.addMessage("A descrição do tipo de moradia deve ser preenchida");
        }

        if (mainFamilyMember.getBirthDate() != null
        && mainFamilyMember.getBirthDate().getTime() > Calendar.getInstance().getTime().getTime()){
            validationExc.addMessage("A data de nascimento não pode ser maior que a data atual");
        }

        if (!validationExc.getValidations().isEmpty()){
            throw validationExc;
        }
    }
}
