package com.cuidar.repository;

import java.util.UUID;

import com.cuidar.model.MainFamilyMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainFamilyMemberRepo extends JpaRepository<MainFamilyMember, UUID> {
}