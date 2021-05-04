package com.cuidar.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

import com.cuidar.dto.DependentFamilyMemberDTO;
import com.cuidar.dto.PageableFamilyMembersDTO;
import com.cuidar.dto.SimpleReturnMainFamilyMemberDTO;
import com.cuidar.dto.FullFamilyDTO;
import com.cuidar.model.DependentFamilyMember;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.service.CreateFamilyService;
import com.cuidar.service.FindDependentFamilyMemberService;
import com.cuidar.service.FindMainFamilyMemberService;
import com.cuidar.service.SearchFamiliesService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("familymembers")
public class FamilyMemberController {
    
    private ModelMapper modelMapper;
    private FindMainFamilyMemberService findMainFamilyMemberService;
    private FindDependentFamilyMemberService findDependentFamilyMemberService;
    private CreateFamilyService createFamilyService;
    private SearchFamiliesService searchFamiliesService;

    public FamilyMemberController(ModelMapper modelMapper, FindMainFamilyMemberService findMainFamilyMemberService, FindDependentFamilyMemberService findDependentFamilyMemberService, CreateFamilyService createFamilyService, SearchFamiliesService searchFamiliesService) {
        this.modelMapper = modelMapper;
        this.findMainFamilyMemberService = findMainFamilyMemberService;
        this.findDependentFamilyMemberService = findDependentFamilyMemberService;
        this.createFamilyService = createFamilyService;
        this.searchFamiliesService = searchFamiliesService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullFamilyDTO> get(@PathVariable(name = "id") UUID mainFamilyMemberId) {
        
        FullFamilyDTO fullFamilyDto = new FullFamilyDTO();
        DependentFamilyMemberDTO dependentMemberDto = new DependentFamilyMemberDTO();

        MainFamilyMember foundMainFamilyMember = this.findMainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId);
        Iterable<DependentFamilyMember> foundDependentFamilyMembers = this.findDependentFamilyMemberService.findAllDependentsMembersFromMain(foundMainFamilyMember);
        
        fullFamilyDto.setMainFamilyMember(fullFamilyDto.getMainFamilyMember().convertToDto(modelMapper, foundMainFamilyMember));
        
        for (DependentFamilyMember dependentFamilyMember : foundDependentFamilyMembers) {
            fullFamilyDto.getDependentMembers().add(dependentMemberDto.convertToDto(modelMapper, dependentFamilyMember));
        }

        return new ResponseEntity<>(fullFamilyDto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<UUID> create(@Valid @RequestBody FullFamilyDTO fullFamilyDTO) {
        MainFamilyMember mainFamilyMember = fullFamilyDTO.getMainFamilyMember().convertToEntity(modelMapper);
        
        Set<DependentFamilyMemberDTO> dependentFamilyDTOMembers = fullFamilyDTO.getDependentMembers();

        Set<DependentFamilyMember> dependentFamilyMembers = new HashSet<>();

        for (DependentFamilyMemberDTO dependentFamilyMemberDTO : dependentFamilyDTOMembers) {
            dependentFamilyMembers.add(dependentFamilyMemberDTO.convertToEntity(modelMapper));
        }
        
        UUID createMainFamilyMemberId = this.createFamilyService.createFamily(mainFamilyMember, dependentFamilyMembers);
        
        return new ResponseEntity<>(createMainFamilyMemberId, HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<PageableFamilyMembersDTO> getAllFamilies(
        @RequestParam(required = false) String fullName,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size){

        List<MainFamilyMember> foundMainFamilyMembersList;
        Page<MainFamilyMember> pageMainFamilyMember;

        if (fullName == null){
            pageMainFamilyMember = this.searchFamiliesService.findAll(page, size);
        }
        else{
            pageMainFamilyMember = this.searchFamiliesService.findByFullName(fullName, page, size);
        }
                                                     
        PageableFamilyMembersDTO foundBySearchFamilyMemberDTO = new PageableFamilyMembersDTO();

        foundBySearchFamilyMemberDTO.setCurrentPage(pageMainFamilyMember.getNumber());
        foundBySearchFamilyMemberDTO.setTotalPages(pageMainFamilyMember.getTotalPages());
        foundBySearchFamilyMemberDTO.setTotalItems(pageMainFamilyMember.getTotalElements());

        foundMainFamilyMembersList = pageMainFamilyMember.getContent();

        for (MainFamilyMember mainFamilyMember : foundMainFamilyMembersList) {
            SimpleReturnMainFamilyMemberDTO simpleMainFamilyMemberDTO = new SimpleReturnMainFamilyMemberDTO();
            foundBySearchFamilyMemberDTO.getFamilies().add(simpleMainFamilyMemberDTO.convertToDto(modelMapper, mainFamilyMember));
        }
        
        return new ResponseEntity<>(foundBySearchFamilyMemberDTO, HttpStatus.OK);
    }
}
