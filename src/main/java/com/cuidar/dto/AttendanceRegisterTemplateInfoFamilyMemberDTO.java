package com.cuidar.dto;

import java.util.UUID;

import com.cuidar.model.enums.FamilyMemberLinkType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceRegisterTemplateInfoFamilyMemberDTO {
    private UUID familyMemberId;
    private String fullName;
    private FamilyMemberLinkType familyLinkType;
}
