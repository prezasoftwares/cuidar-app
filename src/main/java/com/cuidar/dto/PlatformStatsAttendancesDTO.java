package com.cuidar.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformStatsAttendancesDTO {
    
    private long attendancesCount;
    private long recentAttendancesCount;
    private List<PlatformStatsGroupedMonthlyAttendanceCountDTO> groupedMonthlyAttendances = new ArrayList<>();
}
