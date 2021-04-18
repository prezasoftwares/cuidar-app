package com.cuidar.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.UUID;

import com.cuidar.exception.DomainValidationException;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberCivilStatus;
import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberSchooling;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ValidateMainFamilyMemberServiceTest {

    @Autowired
    private ValidateMainFamilyMemberService validateMainFamilyMemberService;

    @Mock
    private MainFamilyMemberRepo mockedMainFamilyMemberRepo;

    private MainFamilyMember regularMainMember;

    @BeforeEach
    public void initializeInstances() {
        regularMainMember = new MainFamilyMember();

        regularMainMember = new MainFamilyMember();
        regularMainMember.setFullName("Full name");
        regularMainMember.setBirthDate(Calendar.getInstance().getTime());
        regularMainMember.setGender(FamilyMemberGender.NoGender);
        regularMainMember.setOccupation("No ocupation");
        regularMainMember.setDocumentId(UUID.randomUUID().toString());
        regularMainMember.setAddressPostalCode("00000-000");
        regularMainMember.setAddressStreetName("STREET");
        regularMainMember.setAddressStreetNumber("123");
        regularMainMember.setAddressCity("CITY");
        regularMainMember.setAddressState("STATE");
        regularMainMember.setCivilStatus(FamilyMemberCivilStatus.Single);
        regularMainMember.setSchooling(FamilyMemberSchooling.Basic);
    }

    @Test
    public void whenValidateBrandNewMainFamilyMember_shouldNotThrowException() {

        assertDoesNotThrow(() -> {
            validateMainFamilyMemberService.validate(regularMainMember);
        });
    }

    @Test
    public void whenValidateMainFamilyMemberWithAlreadyExistentDocumentId_shouldThrowException() {

        // mock a chamada do repositório pra simular que já foi cadastrado outro membro com o documento informado
        when(mockedMainFamilyMemberRepo.existFamilyMemberByDocumentId(anyString())).thenReturn(true);

        assertThrows(DomainValidationException.class, () -> {
            validateMainFamilyMemberService.setMainFamilyMemberRepo(mockedMainFamilyMemberRepo);
            validateMainFamilyMemberService.validate(regularMainMember);
        });

    }

    @Test
    public void whenValidateMainFamilyMemberWithBirthDateGreaterThanToday_shouldThrowException() {
        assertThrows(DomainValidationException.class, () -> {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, 1);

            regularMainMember.setBirthDate(calendar.getTime());
            validateMainFamilyMemberService.validate(regularMainMember);
        });
    }
}
