package com.cuidar.service;

import java.util.UUID;

import com.cuidar.exception.ResourceNotFoundException;
import com.cuidar.model.DependentFamilyMember;
import com.cuidar.repository.DependentFamilyMemberRepo;

import org.springframework.stereotype.Service;

@Service
public class FindDependentFamilyMemberService {
    private DependentFamilyMemberRepo dependentFMRepo;

    public FindDependentFamilyMemberService(DependentFamilyMemberRepo dependentFMRepo) {
        this.dependentFMRepo = dependentFMRepo;
    }

    public DependentFamilyMember findDependentFamilyMemberById(UUID dependentFamilyMemberId) {
        var dependentMember = this.dependentFMRepo.findById(dependentFamilyMemberId);

        if (dependentMember.isPresent()) {
            return dependentMember.get();
        } else {
            throw new ResourceNotFoundException("Membro dependente não foi encontrado", dependentFamilyMemberId.toString());
        }
    }
}
