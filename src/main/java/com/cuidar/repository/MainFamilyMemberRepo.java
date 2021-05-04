package com.cuidar.repository;

import java.util.UUID;

import com.cuidar.model.MainFamilyMember;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainFamilyMemberRepo extends JpaRepository<MainFamilyMember, UUID> {
    boolean existsMainFamilyMemberByDocumentId(String documentId);
    Page<MainFamilyMember> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);
}