package com.cuidar.model;


import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "family_attendance_record_linked_member")
public class FamilyAttendanceRecordLinkedMember extends BaseModel {
    
    @ManyToOne
    @JoinColumn(name = "FK_attendanceReport")
    private FamilyAttendanceRecord attendanceReport;

    @ManyToOne
    @JoinColumn(name = "FK_referencedFamilyMemberId")
    private FamilyMember referencedFamilyMember;    
}
