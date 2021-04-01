package com.cuidar.repository;

import com.cuidar.model.DependentFamilyMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DependentFamilyMemberRepo extends JpaRepository<DependentFamilyMember, Long>, FamilyMemberRepositoryCustom{}