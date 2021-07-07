package com.cuidar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cuidar.model.enums.FamilyMemberNoYesFlag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "family_attendance_record")
public class FamilyAttendanceRecord extends BaseModel {
    @Column(nullable = false)
    private Date attendanceDateTime;
    
    @Column(nullable = false)
    private String notes;

    @ManyToOne
    @JoinColumn(name = "FK_mainFamilyMemberId")
    private MainFamilyMember mainFamilyMember;

    private String summaryFamilyMembers;
    private String summaryActionPlanItems;

    private FamilyMemberNoYesFlag abscence;
}
