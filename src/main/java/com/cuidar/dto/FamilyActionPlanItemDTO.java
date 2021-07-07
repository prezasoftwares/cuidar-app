package com.cuidar.dto;

import java.util.Date;
import java.util.UUID;

import com.cuidar.model.FamilyActionPlanItem;
import com.cuidar.model.enums.FamilyMemberNoYesFlag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilyActionPlanItemDTO extends DTOMapper<FamilyActionPlanItemDTO, FamilyActionPlanItem> {
    
    private String description;
    private Date dueDate;
    private UUID referencedFamilyMember_Id;
    private String referencedFamilyMember_Name;
    private FamilyMemberNoYesFlag isAssistentTask;
    private FamilyMemberNoYesFlag done;
    private Date systemRegistrationDate;
    private UUID id;

    public FamilyActionPlanItemDTO() {
        super(FamilyActionPlanItemDTO.class, FamilyActionPlanItem.class);
    }
}
