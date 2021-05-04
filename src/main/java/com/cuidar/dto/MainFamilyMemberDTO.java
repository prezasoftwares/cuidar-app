package com.cuidar.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberCivilStatus;
import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberGeneralStatus;
import com.cuidar.model.enums.FamilyMemberHousingType;
import com.cuidar.model.enums.FamilyMemberNoYesFlag;
import com.cuidar.model.enums.FamilyMemberSchooling;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainFamilyMemberDTO extends DTOMapper<MainFamilyMemberDTO, MainFamilyMember> {

    public MainFamilyMemberDTO() {
        super(MainFamilyMemberDTO.class, MainFamilyMember.class);
    }

    @NotBlank(message = "Nome completo deve ser preenchido")
    private String fullName;
    @NotNull(message = "Data de nascimento deve ser preenchida")
    private Date birthDate;
    @NotNull(message = "Gênero deve ser preenchido")
    private FamilyMemberGender gender;
    @NotBlank(message = "Ocupação deve ser preenchida")
    private String occupation;

    @NotBlank(message = "Número de documento deve ser preenchido")
    private String documentId;
    @NotBlank(message = "Código postal deve ser preenchido")
    private String addressPostalCode;
    @NotBlank(message = "Nome do logradouro deve ser preenchido")
    private String addressStreetName;
    @NotBlank(message = "Número do logradouro deve ser preenchido")
    private String addressStreetNumber;
    @NotBlank(message = "Cidade deve ser preenchido")
    private String addressCity;
    @NotBlank(message = "Estado deve ser preenchido")
    private String addressState;
    @NotNull(message = "Estado civil deve ser preenchido")
    private FamilyMemberCivilStatus civilStatus;
    @NotNull(message = "Escolaridade deve ser preenchido")
    private FamilyMemberSchooling schooling;

    private String addressStreetComplement;
    private String contactPhoneNumber;
    private String contactEmail;
    private FamilyMemberHousingType housingType;
    private String housingTypeNotes;
    private String economicSituationNotes;
    private String religionNotes;
    private FamilyMemberNoYesFlag baptizedChildren;
    private String socialAssistenceNeedsNotes;
    private Date systemRegistrationDate;

    private @Setter(value = AccessLevel.PRIVATE) FamilyMemberGeneralStatus generalStatus;
}
