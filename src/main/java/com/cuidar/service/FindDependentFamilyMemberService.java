package com.cuidar.service;

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
}
