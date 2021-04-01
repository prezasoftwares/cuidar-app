package com.cuidar.dto;

import com.cuidar.model.DependentFamilyMember;

//Encapsular as propriedades do dependente,
// e não expor os dados do Membro Principal (e nem solicitar um payLoad com os dados do Membro Principal)
public class DependentMemberDTO {
    private DependentFamilyMember dependentMember;
    private Long mainFamilyMemberId;

    public DependentMemberDTO(Long mainFamilyMemberId, DependentFamilyMember dependentMember) {
        this.mainFamilyMemberId = mainFamilyMemberId;
        this.dependentMember = dependentMember;
    }

    public DependentFamilyMember getDependentMember() {
        return dependentMember;
    }

    public Long getMainFamilyMemberId() {
        return mainFamilyMemberId;
    }

    public void setMainFamilyMember(Long mainFamilyMemberId) {
        this.mainFamilyMemberId = mainFamilyMemberId;
    }
    public void setDependentMember(DependentFamilyMember dependentMember) {
        this.dependentMember = dependentMember;
    }
}
