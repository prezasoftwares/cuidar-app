package com.cuidar.repository;

import java.util.UUID;

import com.cuidar.model.FamilyStatusUpdateRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyStatusUpdateRecordRepo extends JpaRepository<FamilyStatusUpdateRecord, UUID> {
    
}
