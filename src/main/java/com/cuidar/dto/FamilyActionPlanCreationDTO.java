package com.cuidar.dto;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import com.cuidar.model.enums.FamilyMemberNoYesFlag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilyActionPlanCreationDTO {
    @NotEmpty(message = "Descrição da ação obrigatória")
    private String description;
    
    private Date dueDate;
    private UUID referencedFamilyMemberId;
    private FamilyMemberNoYesFlag isAssistentTask;
}
