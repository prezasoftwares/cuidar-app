package com.cuidar.repository;

import java.util.List;

import com.cuidar.model.DependentFamilyMember;

public interface FamilyMemberRepositoryCustom {
    List<DependentFamilyMember> findDependentFamlilyMembers(Long mainFamilyMemberId);
}
