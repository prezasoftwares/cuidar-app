package com.cuidar.service;

import java.util.UUID;

import com.cuidar.exception.ResourceNotFoundException;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.springframework.stereotype.Service;

@Service
public class FindMainFamilyMemberService {
    private MainFamilyMemberRepo mainFamilyMemberRepo;

    public FindMainFamilyMemberService(MainFamilyMemberRepo mainFamilyMemberRepo) {
        this.mainFamilyMemberRepo = mainFamilyMemberRepo;
    }

    public MainFamilyMember findMainFamilyMemberById(UUID mainFamilyMemberId) {
        var mainMember = this.mainFamilyMemberRepo.findById(mainFamilyMemberId);

        if (mainMember.isPresent()) {
            return mainMember.get();
        } else {
            throw new ResourceNotFoundException("Membro principal não foi encontrado", mainFamilyMemberId.toString());
        }
    }
}
