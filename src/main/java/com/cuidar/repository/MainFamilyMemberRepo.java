package com.cuidar.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberGeneralStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MainFamilyMemberRepo extends JpaRepository<MainFamilyMember, UUID> {
    boolean existsMainFamilyMemberByDocumentId(String documentId);
    Page<MainFamilyMember> findByFullNameContainingIgnoreCase(String fullName, Pageable pageable);
    long countByassistenceDueDateBetween(Date fromDate, Date toDate);
    long countByassistenceDueDateLessThan(Date date);
    long countByGeneralStatus(FamilyMemberGeneralStatus generalStatus);

    @Query("SELECT f.gender as identifier, COUNT(f) as count FROM FamilyMember f GROUP BY f.gender")
    List<Object[]> findFamilyMemberGenderCount();

    @Query("SELECT f.gender as gender, f.birthDate as birthDate, COUNT(f) as count FROM FamilyMember f GROUP BY f.gender, f.birthDate")
    List<Object[]> findFamilyMemberGenderAndAgeCount();

    @Query("SELECT f.birthDate as identifier, COUNT(f) as count FROM FamilyMember f GROUP BY f.birthDate")
    List<Object[]> findFamilyMemberBirthDateCount();
}