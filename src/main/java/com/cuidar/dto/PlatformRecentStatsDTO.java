package com.cuidar.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformRecentStatsDTO {
    
    private long totalCount;
    private long recentCount;
    private List<PlatformRecentStatsByMonthDTO> groupedMonthlyCount = new ArrayList<>();
}
