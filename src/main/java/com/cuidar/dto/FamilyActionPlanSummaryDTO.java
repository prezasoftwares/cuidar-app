package com.cuidar.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FamilyActionPlanSummaryDTO extends FamilyActionPlanItemsListDTO {

    private int concludedActions = 0;
    
    public FamilyActionPlanSummaryDTO(UUID mainFamilyMemberId) {
        super(mainFamilyMemberId);
    }
}
