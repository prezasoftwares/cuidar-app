package com.cuidar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformStatsGroupedAgeCountDTO {
    private int age;
    private long count;
    
    public PlatformStatsGroupedAgeCountDTO(int identifier, long count) {
        this.age = identifier;
        this.count = count;
    }
}
