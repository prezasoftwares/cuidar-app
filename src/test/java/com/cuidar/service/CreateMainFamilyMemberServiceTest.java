package com.cuidar.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Calendar;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

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

@SpringBootTest
public class CreateMainFamilyMemberServiceTest {
    
    @Autowired
    private CreateMainFamilyMemberService createMainFamilyMemberService;

    private MainFamilyMember allMandatoryFieldsInstance;
    private MainFamilyMember allFieldsInstance;
    private MainFamilyMember noFieldsInstance;

    @BeforeEach
    public void initializeInstances(){
        noFieldsInstance = new MainFamilyMember();

        allMandatoryFieldsInstance = new MainFamilyMember();
        allMandatoryFieldsInstance.setFullName("Full name");
        allMandatoryFieldsInstance.setBirthDate(Calendar.getInstance().getTime());
        allMandatoryFieldsInstance.setGender(FamilyMemberGender.NoGender);
        allMandatoryFieldsInstance.setOccupation("No ocupation");
        allMandatoryFieldsInstance.setDocumentId("1234567");
        allMandatoryFieldsInstance.setAddressPostalCode("00000-000");
        allMandatoryFieldsInstance.setAddressStreetName("STREET");
        allMandatoryFieldsInstance.setAddressStreetNumber("123");
        allMandatoryFieldsInstance.setAddressCity("CITY");
        allMandatoryFieldsInstance.setAddressState("STATE");
        allMandatoryFieldsInstance.setCivilStatus(FamilyMemberCivilStatus.Single);
        allMandatoryFieldsInstance.setSchooling(FamilyMemberSchooling.Basic);

        allFieldsInstance = new MainFamilyMember();
        allFieldsInstance.setFullName("Full name");
        allFieldsInstance.setBirthDate(Calendar.getInstance().getTime());
        allFieldsInstance.setGender(FamilyMemberGender.NoGender);
        allFieldsInstance.setOccupation("No ocupation");
        allFieldsInstance.setDocumentId("1234567");
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
       
        assertThrows(ConstraintViolationException.class, () ->
        {            
            createMainFamilyMemberService.createMainFamilyMember(noFieldsInstance);
        });
    }

    @Test
    public void whenSaveMainFamilyMemberWithAllMandatoryFields_shouldReturnMainMemberCreatedId(){
        UUID createdUUID = createMainFamilyMemberService.createMainFamilyMember(allMandatoryFieldsInstance);

        assertNotNull(createdUUID);
    }

    @Test
    public void whenSaveMainFamilyMemberWithAllPossibleFields_shouldReturnMainMemberCreatedId(){
        
        UUID createdUUID = createMainFamilyMemberService.createMainFamilyMember(allFieldsInstance);

        assertNotNull(createdUUID);
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
}
