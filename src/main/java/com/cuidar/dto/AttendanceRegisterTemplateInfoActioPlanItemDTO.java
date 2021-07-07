package com.cuidar.dto;

import java.util.Date;
import java.util.UUID;

import com.cuidar.model.enums.FamilyMemberNoYesFlag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceRegisterTemplateInfoActioPlanItemDTO {
    private UUID actionPlanItemId;
    private String description;
    private Date dueDate;
    private String referencedFamilyMember_Name;
    private FamilyMemberNoYesFlag isAssistentTask;
}
