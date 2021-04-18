package com.cuidar.repository;

import java.util.List;
import java.util.UUID;

import com.cuidar.model.DependentFamilyMember;

public interface FamilyMemberRepositoryCustom {
    List<DependentFamilyMember> findDependentFamlilyMembers(UUID mainFamilyMemberId);
    boolean existFamilyMemberByDocumentId(String documentId);
}
