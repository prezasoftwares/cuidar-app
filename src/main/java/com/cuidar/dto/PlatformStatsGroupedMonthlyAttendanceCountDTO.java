package com.cuidar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformStatsGroupedMonthlyAttendanceCountDTO {
    private String month;
    private long count;
    
    public PlatformStatsGroupedMonthlyAttendanceCountDTO(String month, long count) {
        this.month = month;
        this.count = count;
    }
}
