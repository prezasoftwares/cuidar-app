package com.cuidar.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceSummaryRecordDTO{
    private Date attendanceDateTime;
    
    private String notes;

    private String linkedFamilyMembersSummary;
    private String linkedActionPlanItemsSummary;
}
