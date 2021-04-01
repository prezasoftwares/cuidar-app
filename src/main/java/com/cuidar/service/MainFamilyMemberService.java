package com.cuidar.service;

import com.cuidar.exception.ResourceNotFoundException;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.springframework.stereotype.Service;


@Service
public class MainFamilyMemberService {
    private MainFamilyMemberRepo mainFMRepo;

    public MainFamilyMemberService(MainFamilyMemberRepo mainFMrepo) {
        this.mainFMRepo = mainFMrepo;
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

    public MainFamilyMember createMainFamilyMember(MainFamilyMember mainFamilyMember) {
        return this.mainFMRepo.save(mainFamilyMember);
    }

    public MainFamilyMember updateMainFamilyMember(Long id, MainFamilyMember mainFamilyMember) {
        mainFamilyMember.setId(id);
        return this.mainFMRepo.save(mainFamilyMember);
    }
}
