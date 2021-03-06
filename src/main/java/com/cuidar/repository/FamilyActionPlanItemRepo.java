package com.cuidar.repository;

import java.util.List;
import java.util.UUID;

import com.cuidar.model.FamilyActionPlanItem;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberNoYesFlag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyActionPlanItemRepo extends JpaRepository<FamilyActionPlanItem, UUID>  {
    public List<FamilyActionPlanItem> findBymainFamilyMember(MainFamilyMember mainFamilyMember);
    public List<FamilyActionPlanItem> findByMainFamilyMemberAndDone(MainFamilyMember mainFamilyMember, FamilyMemberNoYesFlag familyMemberNoYesFlag);
}
