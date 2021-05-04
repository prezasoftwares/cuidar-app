package com.cuidar.service;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
public class SearchFamiliesService {
    private MainFamilyMemberRepo mainFamilyMemberRepo;

    public SearchFamiliesService(MainFamilyMemberRepo mainFamilyMemberRepo) {
        this.mainFamilyMemberRepo = mainFamilyMemberRepo;
    }

    public Page<MainFamilyMember> findAll(int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        Page<MainFamilyMember> pageMainFamilyMembers = this.mainFamilyMemberRepo.findAll(paging);

        return pageMainFamilyMembers;
    }

    public Page<MainFamilyMember> findByFullName(String fullName, int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        Page<MainFamilyMember> pageMainFamilyMembers = this.mainFamilyMemberRepo
                .findByFullNameContainingIgnoreCase(fullName, paging);

        return pageMainFamilyMembers;
    }
}
