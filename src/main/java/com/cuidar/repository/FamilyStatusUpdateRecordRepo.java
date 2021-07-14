package com.cuidar.repository;

import java.util.Date;
import java.util.UUID;

import com.cuidar.model.FamilyStatusUpdateRecord;
import com.cuidar.model.enums.FamilyMemberGeneralStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyStatusUpdateRecordRepo extends JpaRepository<FamilyStatusUpdateRecord, UUID> {
    long countBycurrentStatus(FamilyMemberGeneralStatus currentStatus);

    @Query("SELECT COUNT(f) as count FROM FamilyStatusUpdateRecord f WHERE f.updateDateTime >= :fromDate AND f.updateDateTime <= :toDate AND f.currentStatus = :currentStatus")
    long countByupdateDateTimeBetweenAndcurrentStatus(Date fromDate, Date toDate, FamilyMemberGeneralStatus currentStatus);
    
}
