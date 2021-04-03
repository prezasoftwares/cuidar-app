package com.cuidar.service;

import com.cuidar.dto.MainMemberCreateUpdateDTO;
import com.cuidar.exception.ResourceNotFoundException;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class MainFamilyMemberService {
    private MainFamilyMemberRepo mainFMRepo;
    private ModelMapper modelMapper;

    public MainFamilyMemberService(MainFamilyMemberRepo mainFMrepo, ModelMapper modelMapper) {
        this.mainFMRepo = mainFMrepo;
        this.modelMapper = modelMapper;
    }

    public Iterable<MainFamilyMember> findAllMainFamilyMembers() {
        return this.mainFMRepo.findAll();
    }

    public MainFamilyMember findMainFamilyMemberById(Long mainFamilyMemberId) {
        var mainMember = this.mainFMRepo.findById(mainFamilyMemberId);

        if (mainMember.isPresent()){
            return mainMember.get();
        }
        else
        {
            throw new ResourceNotFoundException("Membro principal não foi encontrado");
        }
    }

    public MainFamilyMember createMainFamilyMember(MainMemberCreateUpdateDTO mainFamilyMemberDto) {
        MainFamilyMember newMainFamilyMember = mainFamilyMemberDto.convertToEntity(this.modelMapper);
        return this.mainFMRepo.save(newMainFamilyMember);
    }

    public MainFamilyMember updateMainFamilyMember(Long id, MainFamilyMember mainFamilyMember) {
        mainFamilyMember.setId(id);
        return this.mainFMRepo.save(mainFamilyMember);
    }
}
