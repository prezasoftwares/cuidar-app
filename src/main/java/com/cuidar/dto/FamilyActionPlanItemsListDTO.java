package com.cuidar.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilyActionPlanItemsListDTO {
    
    private UUID mainFamilyMemberId;
    private Integer actionItemsCount = 0;

    public FamilyActionPlanItemsListDTO(UUID mainFamilyMemberId) {
        this.mainFamilyMemberId = mainFamilyMemberId;
    }

    private Set<FamilyActionPlanItemDTO> actionList = new HashSet<>();
    
}
