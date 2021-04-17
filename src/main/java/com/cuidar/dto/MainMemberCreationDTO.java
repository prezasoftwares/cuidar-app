package com.cuidar.dto;

import java.util.Calendar;
import java.util.UUID;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberCivilStatus;
import com.cuidar.model.enums.FamilyMemberGender;

import org.modelmapper.ModelMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MainMemberCreationDTO {
    
    private UUID id;
    //mandatory
    private String name;
    private String documentId;
    private Calendar birthDate;
    private FamilyMemberGender gender;
    private String addressPostalCode;
    private String addressStreetName;
    private String addressStreetNumber;
    private String addressCity;
    private String addressState;
    private FamilyMemberCivilStatus civilStatus;
    
    //not-mandatory
    private String addressStreetComplement;
    
    private String contactPhoneNumber;
    private String contactEmail;   

    public MainFamilyMember convertToEntity(ModelMapper modelMapper){
        MainFamilyMember mainFamilyMember = modelMapper.map(this, MainFamilyMember.class);
        
        return mainFamilyMember;
    }

    public MainMemberCreationDTO convertToDto(ModelMapper modelMapper, MainFamilyMember mainFamilyMember) {
        MainMemberCreationDTO mainMemberDto = modelMapper.map(mainFamilyMember, MainMemberCreationDTO.class);
        
        return mainMemberDto;
    }
}
