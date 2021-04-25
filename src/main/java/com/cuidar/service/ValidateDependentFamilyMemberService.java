package com.cuidar.service;

import java.util.Calendar;

import com.cuidar.exception.DomainValidationException;
import com.cuidar.model.DependentFamilyMember;

import org.springframework.stereotype.Service;

@Service
public class ValidateDependentFamilyMemberService {
    
    public ValidateDependentFamilyMemberService() {
    }

    public void validate(DependentFamilyMember dependentFamilyMember) {
        DomainValidationException validationExc = new DomainValidationException("Validação dos dados do membro dependente");

        if (dependentFamilyMember.getBirthDate() != null
        && dependentFamilyMember.getBirthDate().getTime() > Calendar.getInstance().getTime().getTime()){
            validationExc.addMessage("A data de nascimento não pode ser maior que a data atual");
        }

        if (!validationExc.getValidations().isEmpty()){
            throw validationExc;
        }
    }
}
