package com.cuidar.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.cuidar.dto.DependentMemberDTO;
import com.cuidar.exception.ResourceNotFoundException;
import com.cuidar.model.DependentFamilyMember;
import com.cuidar.repository.DependentFamilyMemberRepo;

import org.springframework.stereotype.Service;

@Service
public class DependentFamilyMemberService {

    private DependentFamilyMemberRepo dependFMRepo;
    private FindMainFamilyMemberService findMainFamilyMemberService;

    public DependentFamilyMemberService(DependentFamilyMemberRepo dependFMRepo,
            FindMainFamilyMemberService findMainFamilyMemberService) {
        this.dependFMRepo = dependFMRepo;
        this.findMainFamilyMemberService = findMainFamilyMemberService;
    }

    public List<DependentMemberDTO> findAllDependents() {

        List<DependentMemberDTO> dependentList = new ArrayList<>();

        var foundDependentsFromRepo = this.dependFMRepo.findAll();

        for (DependentFamilyMember dependentFamilyMember : foundDependentsFromRepo) {
            dependentList
                    .add(new DependentMemberDTO(dependentFamilyMember.getMainFamilyMember_Id(), dependentFamilyMember));
        }

        return dependentList;
    }

    public Iterable<DependentFamilyMember> findAllDependentsMembersFromMain(UUID id) {
        return this.dependFMRepo.findDependentFamlilyMembers(id);
    }

    public DependentFamilyMember createDependentFamilyMember(DependentMemberDTO dependentMemberDTO) {
        var mainMember = this.findMainFamilyMemberService
                .findMainFamilyMemberById(dependentMemberDTO.getMainFamilyMemberId());

        var dependentFamilyMember = dependentMemberDTO.getDependentMember();
        dependentFamilyMember.setMainFamilyMember(mainMember);

        return this.dependFMRepo.save(dependentFamilyMember);
    }

    public DependentFamilyMember findMDependentFamilyMemberById(UUID id) {
        if (this.dependFMRepo.findById(id).isPresent()) {
            return this.dependFMRepo.getOne(id);
        } else {
            throw new ResourceNotFoundException("Membro dependente não encontrado", id.toString());
        }
    }
}
