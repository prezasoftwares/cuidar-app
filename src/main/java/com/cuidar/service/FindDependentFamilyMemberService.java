package com.cuidar.service;

import java.util.UUID;

import com.cuidar.exception.ResourceNotFoundException;
import com.cuidar.model.DependentFamilyMember;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.repository.DependentFamilyMemberRepo;

import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Setter
@Getter
public class FindDependentFamilyMemberService {
    
    private DependentFamilyMemberRepo dependentFamilyMemberRepo;

    public FindDependentFamilyMemberService(DependentFamilyMemberRepo dependentFamilyMemberRepo) {
        this.dependentFamilyMemberRepo = dependentFamilyMemberRepo;
    }

    public Iterable<DependentFamilyMember> findAllDependentsMembersFromMain(MainFamilyMember mainFamilyMember) {
        return this.dependentFamilyMemberRepo.findBymainFamilyMember(mainFamilyMember);
    }

    public DependentFamilyMember findDependentFamilyMemberById(UUID dependentFamilyMemberId) {
        var dependentMember = this.dependentFamilyMemberRepo.findById(dependentFamilyMemberId);

        if (dependentMember.isPresent()) {
            return dependentMember.get();
        } else {
            throw new ResourceNotFoundException("Membro dependente não foi encontrado", dependentFamilyMemberId.toString());
        }
    }
}
