package com.cuidar.dto;

import java.util.UUID;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberGeneralStatus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SimpleReturnMainFamilyMemberDTO extends DTOMapper<SimpleReturnMainFamilyMemberDTO, MainFamilyMember> {

    public SimpleReturnMainFamilyMemberDTO() {
        super(SimpleReturnMainFamilyMemberDTO.class, MainFamilyMember.class);
    }

    private UUID Id;
    private String fullName;
    private String documentId;
    private FamilyMemberGeneralStatus generalStatus;
}
