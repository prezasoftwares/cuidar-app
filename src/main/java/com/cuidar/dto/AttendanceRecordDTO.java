package com.cuidar.dto;

import java.util.Date;
import java.util.HashSet;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceRecordDTO{

    @NotNull
    private Date attendanceDateTime;
    
    @NotNull
    @NotEmpty
    private String notes;

    private HashSet<AttendanceLinkedFamilyMembersDTO> linkedFamilyMembers = new HashSet<>();
    private HashSet<AttendanceLinkedActionDTO> linkedActionPlanItems = new HashSet<>();
}
