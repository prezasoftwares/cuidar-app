package com.cuidar.dto;

import java.util.ArrayList;
import java.util.List;

import com.cuidar.model.enums.FamilyMemberGender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformStatsGroupedGenderAndAgesCountDTO {
    private FamilyMemberGender gender;
    private long count = 0L;
    private List<PlatformStatsGroupedAgeCountDTO> groupedAges = new ArrayList<>();
    
    public PlatformStatsGroupedGenderAndAgesCountDTO(FamilyMemberGender identifier) {
        this.gender = identifier;
    }
}
