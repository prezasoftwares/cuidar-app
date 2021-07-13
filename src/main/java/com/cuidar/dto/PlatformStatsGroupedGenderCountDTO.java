package com.cuidar.dto;

import com.cuidar.model.enums.FamilyMemberGender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformStatsGroupedGenderCountDTO {
    private FamilyMemberGender gender;
    private long count;
    
    public PlatformStatsGroupedGenderCountDTO(FamilyMemberGender identifier, long count) {
        this.gender = identifier;
        this.count = count;
    }
}
