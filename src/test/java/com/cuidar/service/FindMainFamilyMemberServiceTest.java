package com.cuidar.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import com.cuidar.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FindMainFamilyMemberServiceTest {
    
    @Autowired
    private FindMainFamilyMemberService findMainFamilyMemberService;

    @Test
    public void WhenFindMainFamilyMemberAndItIsNotFound_shouldThrowException(){
        assertThrows(ResourceNotFoundException.class, () -> 
        {
            UUID freshUUID = UUID.randomUUID();

            findMainFamilyMemberService.findMainFamilyMemberById(freshUUID);
        });
    }
}
