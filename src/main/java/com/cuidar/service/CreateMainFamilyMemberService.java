package com.cuidar.service;

import java.util.UUID;

import javax.validation.Valid;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class CreateMainFamilyMemberService {
    private MainFamilyMemberRepo mainFMRepo;

    public CreateMainFamilyMemberService(MainFamilyMemberRepo mainFMrepo) {
        this.mainFMRepo = mainFMrepo;
    }

    public UUID createMainFamilyMember(@Valid MainFamilyMember newMainFamilyMember) {
        MainFamilyMember savedFamilyMember = this.mainFMRepo.save(newMainFamilyMember);

        return savedFamilyMember.getId();
    }
}
