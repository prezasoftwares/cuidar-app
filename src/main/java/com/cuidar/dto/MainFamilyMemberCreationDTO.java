package com.cuidar.dto;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberCivilStatus;
import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberHousingType;
import com.cuidar.model.enums.FamilyMemberNoYesFlag;
import com.cuidar.model.enums.FamilyMemberSchooling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainFamilyMemberCreationDTO extends DTOMapper<MainFamilyMemberCreationDTO, MainFamilyMember> {

    public MainFamilyMemberCreationDTO() {
        super(MainFamilyMemberCreationDTO.class, MainFamilyMember.class);
    }

    // mandatory-super class
    @NotEmpty
    private String fullName;
    @NotNull
    private Date birthDate;
    @NotNull
    private FamilyMemberGender gender;
    @NotEmpty
    private String occupation;

    // mandatory
    @NotEmpty
    private String documentId;
    @NotEmpty
    private String addressPostalCode;
    @NotEmpty
    private String addressStreetName;
    @NotEmpty
    private String addressStreetNumber;
    @NotEmpty
    private String addressCity;
    @NotEmpty
    private String addressState;
    @NotNull
    private FamilyMemberCivilStatus civilStatus;
    @NotNull
    private FamilyMemberSchooling schooling;

    // not-mandatory
    private String addressStreetComplement;
    private String contactPhoneNumber;
    private String contactEmail;
    private FamilyMemberHousingType housingType;
    private String housingTypeNotes;
    private String economicSituationNotes;
    private String religionNotes;
    private FamilyMemberNoYesFlag baptizedChildren;
    private String socialAssistenceNeedsNotes;
}
