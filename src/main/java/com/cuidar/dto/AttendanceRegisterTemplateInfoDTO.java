package com.cuidar.dto;

import java.util.HashSet;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceRegisterTemplateInfoDTO {
    private HashSet<AttendanceRegisterTemplateInfoActioPlanItemDTO> actionPlanItems = new HashSet<>();
    private HashSet<AttendanceRegisterTemplateInfoFamilyMemberDTO> familyMembers = new HashSet<>();
}
