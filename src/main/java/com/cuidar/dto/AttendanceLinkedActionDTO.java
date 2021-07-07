package com.cuidar.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceLinkedActionDTO {
    private UUID actionPlanItemId;
    private String description;
}
