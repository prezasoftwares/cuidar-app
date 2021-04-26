package com.cuidar.repository;

import java.util.List;
import java.util.UUID;

import com.cuidar.model.DependentFamilyMember;
import com.cuidar.model.MainFamilyMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependentFamilyMemberRepo extends JpaRepository<DependentFamilyMember, UUID> {
  public List<DependentFamilyMember> findBymainFamilyMember(MainFamilyMember mainFamilyMember);
}