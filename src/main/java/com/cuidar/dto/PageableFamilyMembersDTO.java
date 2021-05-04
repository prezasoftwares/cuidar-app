package com.cuidar.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageableFamilyMembersDTO {
    
    private int currentPage;
    private Long totalItems;
    private int totalPages;
    private List<SimpleReturnMainFamilyMemberDTO> families = new ArrayList<SimpleReturnMainFamilyMemberDTO>();
}
