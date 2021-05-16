package com.cuidar.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.cuidar.exception.DomainValidationException;
import com.cuidar.model.enums.FamilyMemberGeneralStatus;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FamilyStatusUpdateServiceTest {
    @Autowired
    private FamilyStatusUpdateService familyStatusUpdateService;

    @Test
    public void whenValidateChangeStatusFromPendingToApproved_shouldNotThrowError(){
        assertDoesNotThrow(() ->{
            familyStatusUpdateService.validateStatusTransition(FamilyMemberGeneralStatus.PendingApproval, FamilyMemberGeneralStatus.Active);
        });
    }

    @Test
    public void whenValidateChangeStatusFromPendingToReproved_shouldNotThrowError(){
        assertDoesNotThrow(() ->{
            familyStatusUpdateService.validateStatusTransition(FamilyMemberGeneralStatus.PendingApproval, FamilyMemberGeneralStatus.Reproved);
        });
    }

    @Test
    public void whenValidateChangeStatusFromPendingToPromoted_shouldThrowError(){
        assertThrows(DomainValidationException.class, () ->{
            familyStatusUpdateService.validateStatusTransition(FamilyMemberGeneralStatus.PendingApproval, FamilyMemberGeneralStatus.Promoted);
        });
    }

    @Test
    public void whenValidateChangeStatusFromPendingToSuspended_shouldThrowError(){
        assertThrows(DomainValidationException.class, () ->{
            familyStatusUpdateService.validateStatusTransition(FamilyMemberGeneralStatus.PendingApproval, FamilyMemberGeneralStatus.Promoted);
        });
    }

    @Test
    public void whenValidateChangeStatusFromPendingToPending_shouldThrowError(){
        assertThrows(DomainValidationException.class, () ->{
            familyStatusUpdateService.validateStatusTransition(FamilyMemberGeneralStatus.PendingApproval, FamilyMemberGeneralStatus.PendingApproval);
        });
    }

    
    @Test
    public void whenValidateChangeStatusFromActiveToPromoted_shouldNotThrowError(){
        assertDoesNotThrow(() ->{
            familyStatusUpdateService.validateStatusTransition(FamilyMemberGeneralStatus.Active, FamilyMemberGeneralStatus.Promoted);
        });
    }

    @Test
    public void whenValidateChangeStatusFromActiveToSuspended_shouldNotThrowError(){
        assertDoesNotThrow(() ->{
            familyStatusUpdateService.validateStatusTransition(FamilyMemberGeneralStatus.Active, FamilyMemberGeneralStatus.Suspended);
        });
    }

    @Test
    public void whenValidateChangeStatusFromActiveToPending_shouldThrowError(){
        assertThrows(DomainValidationException.class, () ->{
            familyStatusUpdateService.validateStatusTransition(FamilyMemberGeneralStatus.Active, FamilyMemberGeneralStatus.PendingApproval);
        });
    }

    @Test
    public void whenValidateChangeStatusFromActiveToReproved_shouldThrowError(){
        assertThrows(DomainValidationException.class, () ->{
            familyStatusUpdateService.validateStatusTransition(FamilyMemberGeneralStatus.Active, FamilyMemberGeneralStatus.Reproved);
        });
    }

    @Test
    public void whenValidateChangeStatusFromActiveToActive_shouldThrowError(){
        assertThrows(DomainValidationException.class, () ->{
            familyStatusUpdateService.validateStatusTransition(FamilyMemberGeneralStatus.Active, FamilyMemberGeneralStatus.Active);
        });
    }

    @Test
    public void whenValidateChangeStatusFromSuspendedToActive_shouldThrowError(){
        assertThrows(DomainValidationException.class, () ->{
            familyStatusUpdateService.validateStatusTransition(FamilyMemberGeneralStatus.Suspended, FamilyMemberGeneralStatus.Active);
        });
    }

    @Test
    public void whenValidateChangeStatusFromPromotedToActive_shouldThrowError(){
        assertThrows(DomainValidationException.class, () ->{
            familyStatusUpdateService.validateStatusTransition(FamilyMemberGeneralStatus.Promoted, FamilyMemberGeneralStatus.Active);
        });
    }
}
