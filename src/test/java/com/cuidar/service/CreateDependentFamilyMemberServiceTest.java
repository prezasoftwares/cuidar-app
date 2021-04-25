package com.cuidar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.UUID;

import com.cuidar.model.DependentFamilyMember;
import com.cuidar.model.enums.FamilyMemberGender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
public class CreateDependentFamilyMemberServiceTest {
    
    @Autowired
    private CreateDependentFamilyMemberService createDependentFamilyMemberService;

    private DependentFamilyMember allMandatoryFieldsInstance;
    private DependentFamilyMember allFieldsInstance;
    private DependentFamilyMember noFieldsInstance;

    @BeforeEach
    public void initializeInstances(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DATE, -1000);
        noFieldsInstance = new DependentFamilyMember();

        allMandatoryFieldsInstance = new DependentFamilyMember();
        allMandatoryFieldsInstance.setFullName("Full name");
        allMandatoryFieldsInstance.setBirthDate(calendar.getTime());
        allMandatoryFieldsInstance.setGender(FamilyMemberGender.NoGender);
        allMandatoryFieldsInstance.setOccupation("No ocupation");
        allMandatoryFieldsInstance.setDocumentId(UUID.randomUUID().toString());
    }

    @Test
    public void whenSaveDependentFamilyMemberWithAllMandatoryFields_shouldReturnMainMemberCreatedId(){
        UUID createdUUID = UUID.randomUUID();
        CreateDependentFamilyMemberService localCreateDependentFamilyService = mock(CreateDependentFamilyMemberService.class);
        
        when(localCreateDependentFamilyService.createDependentFamilyMember(allMandatoryFieldsInstance)).thenReturn(createdUUID);

        assertEquals(createdUUID, localCreateDependentFamilyService.createDependentFamilyMember(allMandatoryFieldsInstance));
    }

    @Test
    public void whenSaveDependentFamilyMemberWithAllPossibleFields_shouldReturnDependentMemberCreatedId(){
        UUID createdUUID = UUID.randomUUID();
        CreateDependentFamilyMemberService localCreateDependentFamilyService = mock(CreateDependentFamilyMemberService.class);
        
        when(localCreateDependentFamilyService.createDependentFamilyMember(allFieldsInstance)).thenReturn(createdUUID);

        assertEquals(createdUUID, localCreateDependentFamilyService.createDependentFamilyMember(allFieldsInstance));
    }
}
