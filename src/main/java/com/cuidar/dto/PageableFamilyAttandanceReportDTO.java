package com.cuidar.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableFamilyAttandanceReportDTO {
    
    private int currentPage;
    private Long totalItems;
    private int totalPages;
    private List<AttendanceSummaryRecordDTO> attendances = new ArrayList<AttendanceSummaryRecordDTO>();
}
