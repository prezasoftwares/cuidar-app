package com.cuidar.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Calendar;
import java.util.UUID;

import com.cuidar.exception.DomainValidationException;
import com.cuidar.model.DependentFamilyMember;
import com.cuidar.model.enums.FamilyMemberGender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidateDependentFamilyMemberServiceTest {
    
    @Autowired
    private ValidateDependentFamilyMemberService validateDependentFamilyMemberService;

    private DependentFamilyMember regularDependentMember;

    @BeforeEach
    public void initializeInstances() {
        regularDependentMember = new DependentFamilyMember();

        regularDependentMember.setFullName("Full name");
        regularDependentMember.setBirthDate(Calendar.getInstance().getTime());
        regularDependentMember.setGender(FamilyMemberGender.NoGender);
        regularDependentMember.setOccupation("No ocupation");
        regularDependentMember.setDocumentId(UUID.randomUUID().toString());
    }

    @Test
    public void whenValidateDependentFamilyMemberWithBirthDateGreaterThanToday_shouldThrowException() {
        assertThrows(DomainValidationException.class, () -> {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);

            regularDependentMember.setBirthDate(calendar.getTime());
            validateDependentFamilyMemberService.validate(regularDependentMember);
        });
    }
}
