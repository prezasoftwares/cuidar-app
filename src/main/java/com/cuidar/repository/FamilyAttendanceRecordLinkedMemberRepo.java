package com.cuidar.repository;

import java.util.UUID;

import com.cuidar.model.FamilyAttendanceRecordLinkedMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyAttendanceRecordLinkedMemberRepo extends JpaRepository<FamilyAttendanceRecordLinkedMember, UUID> {
}
