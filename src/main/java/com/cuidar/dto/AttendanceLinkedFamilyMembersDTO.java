package com.cuidar.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendanceLinkedFamilyMembersDTO {
    private UUID familyMemberId;
    private String name;
}
