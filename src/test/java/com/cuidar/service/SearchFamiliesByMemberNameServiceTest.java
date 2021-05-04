package com.cuidar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SearchFamiliesByMemberNameServiceTest {

    @Autowired
    private SearchFamiliesService searchFamiliesByMemberNameService;

    @Test
    public void WhenSearchFamilyMembersByNameAndItIsNotFound_shouldReturnEmptyList(){
        
        assertEquals(searchFamiliesByMemberNameService.findByFullName("xucrute", 1, 10).getNumberOfElements(), 0);
    }
}
