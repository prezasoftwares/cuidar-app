package com.cuidar.dto;

import java.util.UUID;

import com.cuidar.model.DependentFamilyMember;

//Encapsular as propriedades do dependente,
// e não expor os dados do Membro Principal (e nem solicitar um payLoad com os dados do Membro Principal)
public class DependentMemberDTO {
    private DependentFamilyMember dependentMember;
    private UUID mainFamilyMemberId;

    public DependentMemberDTO(UUID mainFamilyMemberId, DependentFamilyMember dependentMember) {
        this.mainFamilyMemberId = mainFamilyMemberId;
        this.dependentMember = dependentMember;
    }

    public DependentFamilyMember getDependentMember() {
        return dependentMember;
    }

    public UUID getMainFamilyMemberId() {
        return mainFamilyMemberId;
    }

    public void setMainFamilyMember(UUID mainFamilyMemberId) {
        this.mainFamilyMemberId = mainFamilyMemberId;
    }
    public void setDependentMember(DependentFamilyMember dependentMember) {
        this.dependentMember = dependentMember;
    }
}
