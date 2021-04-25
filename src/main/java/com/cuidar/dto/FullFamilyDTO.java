package com.cuidar.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FullFamilyDTO {

    public FullFamilyDTO() {
    }

    @NotNull(message = "Faltam os dados do membro principal")
    @Valid
    private MainFamilyMemberDTO mainFamilyMember = new MainFamilyMemberDTO();
    
    @NotNull(message = "Falta os dados dos membros dependentes")
    @Valid
    private Set<DependentFamilyMemberDTO> dependentMembers = new HashSet<>();    
}
