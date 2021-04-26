package com.cuidar.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.cuidar.model.DependentFamilyMember;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberCivilStatus;
import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberSchooling;
import com.cuidar.repository.DependentFamilyMemberRepo;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CreateFamilyServiceTest {
    
    @Autowired
    private CreateMainFamilyMemberService createMainFamilyMemberService;

    @Autowired 
    private CreateDependentFamilyMemberService createDependentFamilyMemberService;

    @Autowired
    private CreateFamilyService createFamilyService;

    private MainFamilyMember allMandatoryFieldsInstanceMainMember;
    private DependentFamilyMember allMandatoryFieldsInstanceDependentMember;

    @BeforeEach
    public void initializeInstances(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, -1000);
        
        allMandatoryFieldsInstanceMainMember = new MainFamilyMember();
        
        allMandatoryFieldsInstanceMainMember = new MainFamilyMember();
        allMandatoryFieldsInstanceMainMember.setFullName("Full name");
        allMandatoryFieldsInstanceMainMember.setBirthDate(calendar.getTime());
        allMandatoryFieldsInstanceMainMember.setGender(FamilyMemberGender.NoGender);
        allMandatoryFieldsInstanceMainMember.setOccupation("No ocupation");
        allMandatoryFieldsInstanceMainMember.setDocumentId(UUID.randomUUID().toString());
        allMandatoryFieldsInstanceMainMember.setAddressPostalCode("00000-000");
        allMandatoryFieldsInstanceMainMember.setAddressStreetName("STREET");
        allMandatoryFieldsInstanceMainMember.setAddressStreetNumber("123");
        allMandatoryFieldsInstanceMainMember.setAddressCity("CITY");
        allMandatoryFieldsInstanceMainMember.setAddressState("STATE");
        allMandatoryFieldsInstanceMainMember.setCivilStatus(FamilyMemberCivilStatus.Single);
        allMandatoryFieldsInstanceMainMember.setSchooling(FamilyMemberSchooling.Basic);

        allMandatoryFieldsInstanceDependentMember = new DependentFamilyMember();
        allMandatoryFieldsInstanceDependentMember.setFullName("Full name dependent");
        allMandatoryFieldsInstanceDependentMember.setBirthDate(calendar.getTime());
        allMandatoryFieldsInstanceDependentMember.setGender(FamilyMemberGender.NoGender);
        allMandatoryFieldsInstanceDependentMember.setOccupation("No ocupation");
        allMandatoryFieldsInstanceDependentMember.setDocumentId(UUID.randomUUID().toString());
        
    }

    @Test
    public void whenSaveFullFamilyWithAllMandatoryFieldsAndNoDependents_shouldReturnCreatedMainMemberUUID(){
        
        MainFamilyMemberRepo mockedMainMemberFamilyRepo = mock(MainFamilyMemberRepo.class);
        MainFamilyMember mockedMainFamilyMember = mock(MainFamilyMember.class);
        FindMainFamilyMemberService mockedFindMainFamilyMemberService = mock(FindMainFamilyMemberService.class);

        mockedFindMainFamilyMemberService.setMainFamilyMemberRepo(mockedMainMemberFamilyRepo);

        UUID mainMemberId = UUID.randomUUID();

        createMainFamilyMemberService.setMainFamilyMemberRepo(mockedMainMemberFamilyRepo);

        createMainFamilyMemberService.getValidateMainFamilyMemberService().setFindMainFamilyMemberService(mockedFindMainFamilyMemberService);
        
        createFamilyService.setCreateMainFamilyMemberService(createMainFamilyMemberService);

        when(mockedMainMemberFamilyRepo.save(any())).thenReturn(mockedMainFamilyMember);
        when(mockedMainMemberFamilyRepo.existsMainFamilyMemberByDocumentId(any())).thenReturn(false);
        when(mockedMainFamilyMember.getId()).thenReturn(mainMemberId);

        Set<DependentFamilyMember> dependentFamilyMembers = new HashSet<>();

        assertEquals(mainMemberId, createFamilyService.createFamily(allMandatoryFieldsInstanceMainMember, dependentFamilyMembers));
    }

    @Test
    public void whenSaveFullFamilyWithAllMandatoryFieldsAndOneDependent_shouldReturnCreatedMainMemberUUID(){

        MainFamilyMemberRepo mockedMainMemberFamilyRepo = mock(MainFamilyMemberRepo.class);
        MainFamilyMember mockedMainFamilyMember = mock(MainFamilyMember.class);
        FindMainFamilyMemberService mockedFindMainFamilyMemberService = mock(FindMainFamilyMemberService.class);

        mockedFindMainFamilyMemberService.setMainFamilyMemberRepo(mockedMainMemberFamilyRepo);
        
        DependentFamilyMemberRepo mockedDependentMemberFamilyRepo = mock(DependentFamilyMemberRepo.class);
        DependentFamilyMember mockedDependentFamilyMember = mock(DependentFamilyMember.class);

        UUID mainMemberId = UUID.randomUUID();
        UUID dependentMemberId = UUID.randomUUID();

        createMainFamilyMemberService.setMainFamilyMemberRepo(mockedMainMemberFamilyRepo);
        createDependentFamilyMemberService.setDependentFamilyMemberRepo(mockedDependentMemberFamilyRepo);

        createMainFamilyMemberService.getValidateMainFamilyMemberService().setFindMainFamilyMemberService(mockedFindMainFamilyMemberService);

        createFamilyService.setCreateDependentFamilyMemberService(createDependentFamilyMemberService);
        createFamilyService.setCreateMainFamilyMemberService(createMainFamilyMemberService);

        when(mockedMainMemberFamilyRepo.save(any())).thenReturn(mockedMainFamilyMember);
        when(mockedMainMemberFamilyRepo.existsMainFamilyMemberByDocumentId(any())).thenReturn(false);
        when(mockedDependentMemberFamilyRepo.save(any())).thenReturn(mockedDependentFamilyMember);

        when(mockedMainFamilyMember.getId()).thenReturn(mainMemberId);
        when(mockedDependentFamilyMember.getId()).thenReturn(dependentMemberId);

        Set<DependentFamilyMember> dependentFamilyMembers = new HashSet<>();
        dependentFamilyMembers.add(allMandatoryFieldsInstanceDependentMember);

        assertEquals(mainMemberId, createFamilyService.createFamily(allMandatoryFieldsInstanceMainMember, dependentFamilyMembers));
    }
}
