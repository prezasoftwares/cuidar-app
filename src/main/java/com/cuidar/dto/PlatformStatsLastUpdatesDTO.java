package com.cuidar.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformStatsLastUpdatesDTO {
    
    private long promotedFamiliesCount;
    private long recentPromotedFamiliesCount;
    private List<PlatformStatsGroupedMonthlyAttendanceCountDTO> groupedPromoted = new ArrayList<>();
}
