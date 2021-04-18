package com.cuidar.service;

import java.util.UUID;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.springframework.stereotype.Service;

@Service
public class CreateMainFamilyMemberService {
    private MainFamilyMemberRepo mainFMRepo;
    private ValidateMainFamilyMemberService validateMainFamilyMemberService;

    public CreateMainFamilyMemberService(MainFamilyMemberRepo mainFMrepo, ValidateMainFamilyMemberService validateMainFamilyMemberService) {
        this.mainFMRepo = mainFMrepo;
        this.validateMainFamilyMemberService = validateMainFamilyMemberService;
    }

    public UUID createMainFamilyMember(MainFamilyMember newMainFamilyMember) {
        validateMainFamilyMemberService.validate(newMainFamilyMember);

        MainFamilyMember savedFamilyMember = this.mainFMRepo.save(newMainFamilyMember);     

        return savedFamilyMember.getId();
    }
}
