package com.cuidar.service;

import java.util.UUID;

import com.cuidar.exception.ResourceNotFoundException;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
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

    public boolean existsMainFamilyMemberByDocumentId(String documentId){
        return mainFamilyMemberRepo.existsMainFamilyMemberByDocumentId(documentId);
    }
}
