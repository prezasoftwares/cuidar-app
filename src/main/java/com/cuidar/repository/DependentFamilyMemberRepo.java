package com.cuidar.repository;

import java.util.List;
import java.util.UUID;

import com.cuidar.model.DependentFamilyMember;
import com.cuidar.model.MainFamilyMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DependentFamilyMemberRepo
        extends JpaRepository<DependentFamilyMember, UUID>, FamilyMemberRepositoryCustom {

  @Query("select d from DependentFamilyMember d where d.mainFamilyMember = :mainFamilyMember")
  public List<DependentFamilyMember> findByMainFamilyMemberId(MainFamilyMember mainFamilyMember);
}