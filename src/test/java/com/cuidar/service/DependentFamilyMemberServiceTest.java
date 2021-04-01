package com.cuidar.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cuidar.exception.ResourceNotFoundException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DependentFamilyMemberServiceTest {
    
    @Autowired
    private DependentFamilyMemberService dependentFamilyMemberService;

    @Test
    void getAllDependentsMemberInTheSystema(){
        var result = this.dependentFamilyMemberService.findAllDependents();

        assertNotNull(result);
    }

    @Test
    void getSpecificDependentMemberById(){
        var result = this.dependentFamilyMemberService.findMDependentFamilyMemberById(4L);

        assertNotNull(result);
    }

    @Test
    void getNotExistentDependentByIdAndThrowsExceptionResourceNotFound(){
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            this.dependentFamilyMemberService.findMDependentFamilyMemberById(9999999L);
        });
    
        String expectedMessage = "Membro dependente não encontrado";
        String actualMessage = exception.getMessage();
    
        assertTrue(actualMessage.contains(expectedMessage));
    }
}
