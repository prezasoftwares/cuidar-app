package com.cuidar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.profiles.active=test")
public class MainFamilymemberServiceTest {
    
    @Autowired
    private MainFamilyMemberService mainFamilyMemberService;

    @Test
    void updateMainFamilyMember() throws Exception{
        var foundMainFamilyMember = mainFamilyMemberService.findMainFamilyMemberById(1L);
        
        foundMainFamilyMember.setName("Novo nome");
        var result = mainFamilyMemberService.updateMainFamilyMember(1L, foundMainFamilyMember);

        assertEquals(1L, result.getId());
        assertEquals("Novo nome", result.getName());
    }
}
