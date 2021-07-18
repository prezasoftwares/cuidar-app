package com.cuidar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformRecentStatsByMonthDTO {
    private String month;
    private long count;
    
    public PlatformRecentStatsByMonthDTO(String month, long count) {
        this.month = month;
        this.count = count;
    }
}
