package com.cuidar.service;

import java.util.Calendar;

import com.cuidar.exception.DomainValidationException;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberHousingType;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class ValidateMainFamilyMemberService {
    private FindMainFamilyMemberService findMainFamilyMemberService;

    public ValidateMainFamilyMemberService(FindMainFamilyMemberService findMainFamilyMemberService) {
        this.findMainFamilyMemberService = findMainFamilyMemberService;    
    }

    public void validate(MainFamilyMember mainFamilyMember) {
        DomainValidationException validationExc = new DomainValidationException("Validação dos dados do membro principal");

        if (this.findMainFamilyMemberService.existsMainFamilyMemberByDocumentId(mainFamilyMember.getDocumentId())){
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
