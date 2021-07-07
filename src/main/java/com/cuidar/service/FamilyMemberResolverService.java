package com.cuidar.service;

import java.util.UUID;

import com.cuidar.exception.ResourceNotFoundException;
import com.cuidar.model.FamilyMember;
import com.cuidar.repository.DependentFamilyMemberRepo;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class FamilyMemberResolverService {
    private MainFamilyMemberRepo mainFamilyMemberRepo;
    private DependentFamilyMemberRepo dependentFamilyMemberRepo;

    public FamilyMemberResolverService(MainFamilyMemberRepo mainFamilyMemberRepo, DependentFamilyMemberRepo dependentFamilyMemberRepo) {
        this.mainFamilyMemberRepo = mainFamilyMemberRepo;
        this.dependentFamilyMemberRepo = dependentFamilyMemberRepo;
    }

    public FamilyMember resolveFamilyMember(UUID familyMemberId) {
        var mainMember = this.mainFamilyMemberRepo.findById(familyMemberId);

        if (mainMember.isPresent()) {
            return mainMember.get();
        } else {
            var dependentMember = this.dependentFamilyMemberRepo.findById(familyMemberId);

            if (dependentMember.isPresent()){
                return dependentMember.get();
            }
        }
        
        throw new ResourceNotFoundException("Membro da família não encontrado", familyMemberId.toString());
    }

    public boolean existsMainFamilyMemberByDocumentId(String documentId){
        return mainFamilyMemberRepo.existsMainFamilyMemberByDocumentId(documentId);
    }
}
