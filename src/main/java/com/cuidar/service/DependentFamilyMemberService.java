package com.cuidar.service;

import java.util.ArrayList;
import java.util.List;

import com.cuidar.dto.DependentMemberDTO;
import com.cuidar.exception.ResourceNotFoundException;
import com.cuidar.model.DependentFamilyMember;
import com.cuidar.repository.DependentFamilyMemberRepo;

import org.springframework.stereotype.Service;

@Service
public class DependentFamilyMemberService{
    
    private DependentFamilyMemberRepo dependFMRepo;
    private MainFamilyMemberService mainFamilyMemberService;

    public DependentFamilyMemberService(DependentFamilyMemberRepo dependFMRepo, MainFamilyMemberService mainFamilyMemberService) {
        this.dependFMRepo = dependFMRepo;
        this.mainFamilyMemberService = mainFamilyMemberService;
    }

    public List<DependentMemberDTO> findAllDependents() {
        
        List<DependentMemberDTO> dependentList = new ArrayList<>();

        var foundDependentsFromRepo = this.dependFMRepo.findAll();

        for (DependentFamilyMember dependentFamilyMember : foundDependentsFromRepo) {
            dependentList.add(new DependentMemberDTO(dependentFamilyMember.getMainFamilyMember_Id(), dependentFamilyMember));
        }
        
        return dependentList;
    }

    public Iterable<DependentFamilyMember> findAllDependentsMembersFromMain(Long id) {
        return this.dependFMRepo.findDependentFamlilyMembers(id);
    }

    public DependentFamilyMember createDependentFamilyMember(DependentMemberDTO dependentMemberDTO){
        var mainMember = this.mainFamilyMemberService.findMainFamilyMemberById(dependentMemberDTO.getMainFamilyMemberId());

        var dependentFamilyMember = dependentMemberDTO.getDependentMember();
        dependentFamilyMember.setMainFamilyMember(mainMember);

        return this.dependFMRepo.save(dependentFamilyMember);
    }

    public DependentFamilyMember findMDependentFamilyMemberById(long id) {
        if (this.dependFMRepo.findById(id).isPresent()){
            return this.dependFMRepo.getOne(id);
        }
        else
        {
            throw new ResourceNotFoundException("Membro dependente não encontrado");
        }
    }
}
