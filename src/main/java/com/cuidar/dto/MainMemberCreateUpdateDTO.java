package com.cuidar.dto;

import java.util.Calendar;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberCivilStatus;
import com.cuidar.model.enums.FamilyMemberGender;

import org.modelmapper.ModelMapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MainMemberCreateUpdateDTO {
    
    private Long id;
    private String documentId;
    private String name;
    private Calendar birthDate;
    private FamilyMemberGender gender;
    private String addressPostalCode;
    private String addressStreetName;
    private String addressStreetNumber;
    private String addressStreetComplement;
    private String addressCity;
    private String addressState;
    private FamilyMemberCivilStatus civilStatus;
    private String contactPhoneNumber;
    private String contactEmail;   

    public MainFamilyMember convertToEntity(ModelMapper modelMapper){
        MainFamilyMember mainFamilyMember = modelMapper.map(this, MainFamilyMember.class);
        
        return mainFamilyMember;
    }

    public MainMemberCreateUpdateDTO convertToDto(ModelMapper modelMapper, MainFamilyMember mainFamilyMember) {
        MainMemberCreateUpdateDTO mainMemberDto = modelMapper.map(mainFamilyMember, MainMemberCreateUpdateDTO.class);
        
        return mainMemberDto;
    }
}
