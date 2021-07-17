package com.cuidar.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlatformStatsFamiliesDTO {
    private long familiesCount;
    private long familyMembersCount;
    private long pendingApprovalCount;
    private long lastMonthFamiliesCount;
    private long expiredFamiliesCount;
    private List<PlatformStatsGroupedGenderAndAgesCountDTO> groupedGendersAndAges = new ArrayList<>();
}
