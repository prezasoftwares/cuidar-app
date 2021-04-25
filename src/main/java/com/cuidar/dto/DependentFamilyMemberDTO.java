package com.cuidar.dto;

import java.sql.Date;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cuidar.model.DependentFamilyMember;
import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberLinkType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DependentFamilyMemberDTO extends DTOMapper<DependentFamilyMemberDTO, DependentFamilyMember> {
    
    @NotBlank(message = "Nome completo deve ser preenchido")
    private String fullName;
    @NotNull(message = "Data de nascimento deve ser preenchida")
    private Date birthDate;
    @NotNull(message = "Gênero deve ser preenchido")
    private FamilyMemberGender gender;
    @NotBlank(message = "Ocupação deve ser preenchida")
    private String occupation;

    private UUID mainFamilyMemberId;
    private String documentId;
    
    @NotNull(message = "Tipo de vínculo deve ser informado")
    private FamilyMemberLinkType linkTypeToMainMember;

    public DependentFamilyMemberDTO() {
        super(DependentFamilyMemberDTO.class, DependentFamilyMember.class);
    }
    
}
