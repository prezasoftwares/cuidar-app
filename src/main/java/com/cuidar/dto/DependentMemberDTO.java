package com.cuidar.dto;

import java.util.UUID;

import com.cuidar.model.DependentFamilyMember;

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
