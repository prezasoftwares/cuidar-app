package com.cuidar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.UUID;

import com.cuidar.exception.DomainValidationException;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberCivilStatus;
import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberHousingType;
import com.cuidar.model.enums.FamilyMemberNoYesFlag;
import com.cuidar.model.enums.FamilyMemberSchooling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
public class CreateMainFamilyMemberServiceTest {
    
    @Autowired
    private CreateMainFamilyMemberService createMainFamilyMemberService;

    private MainFamilyMember allMandatoryFieldsInstance;
    private MainFamilyMember allFieldsInstance;
    private MainFamilyMember noFieldsInstance;

    @BeforeEach
    public void initializeInstances(){
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DATE, -1000);
        noFieldsInstance = new MainFamilyMember();

        allMandatoryFieldsInstance = new MainFamilyMember();
        allMandatoryFieldsInstance.setFullName("Full name");
        allMandatoryFieldsInstance.setBirthDate(calendar.getTime());
        allMandatoryFieldsInstance.setGender(FamilyMemberGender.NoGender);
        allMandatoryFieldsInstance.setOccupation("No ocupation");
        allMandatoryFieldsInstance.setDocumentId(UUID.randomUUID().toString());
        allMandatoryFieldsInstance.setAddressPostalCode("00000-000");
        allMandatoryFieldsInstance.setAddressStreetName("STREET");
        allMandatoryFieldsInstance.setAddressStreetNumber("123");
        allMandatoryFieldsInstance.setAddressCity("CITY");
        allMandatoryFieldsInstance.setAddressState("STATE");
        allMandatoryFieldsInstance.setCivilStatus(FamilyMemberCivilStatus.Single);
        allMandatoryFieldsInstance.setSchooling(FamilyMemberSchooling.Basic);

        allFieldsInstance = new MainFamilyMember();
        allFieldsInstance.setFullName("Full name");
        allFieldsInstance.setBirthDate(calendar.getTime());
        allFieldsInstance.setGender(FamilyMemberGender.NoGender);
        allFieldsInstance.setOccupation("No ocupation");
        allFieldsInstance.setDocumentId(UUID.randomUUID().toString());
        allFieldsInstance.setAddressPostalCode("00000-000");
        allFieldsInstance.setAddressStreetName("STREET");
        allFieldsInstance.setAddressStreetNumber("123");
        allFieldsInstance.setAddressCity("CITY");
        allFieldsInstance.setAddressState("STATE");
        allFieldsInstance.setCivilStatus(FamilyMemberCivilStatus.Single);
        allFieldsInstance.setSchooling(FamilyMemberSchooling.Basic);
        allFieldsInstance.setAddressStreetComplement("STREET CMPLT");
        allFieldsInstance.setContactPhoneNumber("119988776655");
        allFieldsInstance.setContactEmail("email@email");
        allFieldsInstance.setHousingType(FamilyMemberHousingType.Own);
        allFieldsInstance.setHousingTypeNotes("Housing notes");
        allFieldsInstance.setEconomicSituationNotes("Economic Situation Notes");
        allFieldsInstance.setReligionNotes("Religion notes");
        allFieldsInstance.setBaptizedChildren(FamilyMemberNoYesFlag.Yes);
        allFieldsInstance.setSocialAssistenceNeedsNotes("Social assistence needs notes");
    }

    @Test
    public void whenSaveMainFamilyMemberWithNoFields_shouldThrowException(){
       
        assertThrows(DataIntegrityViolationException.class, () ->
        {            
            createMainFamilyMemberService.createMainFamilyMember(noFieldsInstance);
        });
    }

    @Test
    public void whenSaveMainFamilyMemberWithAllMandatoryFields_shouldReturnMainMemberCreatedId(){
        UUID createdUUID = UUID.randomUUID();
        CreateMainFamilyMemberService localCreateMainFamilyService = mock(CreateMainFamilyMemberService.class);
        
        when(localCreateMainFamilyService.createMainFamilyMember(allMandatoryFieldsInstance)).thenReturn(createdUUID);

        assertEquals(createdUUID, localCreateMainFamilyService.createMainFamilyMember(allMandatoryFieldsInstance));
    }

    @Test
    public void whenSaveMainFamilyMemberWithAllPossibleFields_shouldReturnMainMemberCreatedId(){
        UUID createdUUID = UUID.randomUUID();
        CreateMainFamilyMemberService localCreateMainFamilyService = mock(CreateMainFamilyMemberService.class);
        
        when(localCreateMainFamilyService.createMainFamilyMember(allFieldsInstance)).thenReturn(createdUUID);

        assertEquals(createdUUID, localCreateMainFamilyService.createMainFamilyMember(allFieldsInstance));
    }

    @Test
    public void whenSaveMainMemberFamilyWithOtherHousingOptionAndNotSpecifyNotes_shouldThrowException(){
        assertThrows(DomainValidationException.class, () ->
        {
            allMandatoryFieldsInstance.setHousingType(FamilyMemberHousingType.Other);
            allMandatoryFieldsInstance.setHousingTypeNotes("");
            createMainFamilyMemberService.createMainFamilyMember(allMandatoryFieldsInstance);
        });
    }

    @Test
    public void whenSaveMainMemberFamilyTwiceSameDocumentId_shouldThrowException(){
        assertThrows(DomainValidationException.class, () ->
        {
            allMandatoryFieldsInstance.setDocumentId("documentId-123");
            createMainFamilyMemberService.createMainFamilyMember(allMandatoryFieldsInstance);

            allMandatoryFieldsInstance.setDocumentId("documentId-123");
            createMainFamilyMemberService.createMainFamilyMember(allMandatoryFieldsInstance);
        });
    }
}
