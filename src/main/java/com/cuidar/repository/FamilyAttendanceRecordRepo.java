package com.cuidar.repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.cuidar.model.FamilyAttendanceRecord;
import com.cuidar.model.MainFamilyMember;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyAttendanceRecordRepo extends JpaRepository<FamilyAttendanceRecord, UUID> {
    public List<FamilyAttendanceRecord> findBymainFamilyMember(MainFamilyMember mainFamilyMember); 
    Page<FamilyAttendanceRecord> findBymainFamilyMember(MainFamilyMember mainFamilyMember, Pageable pageable);
    public long countByattendanceDateTimeBetween(Date fromDate, Date toDate);
}
