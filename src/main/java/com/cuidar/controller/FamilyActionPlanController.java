package com.cuidar.controller;

import java.util.UUID;

import javax.validation.Valid;

import com.cuidar.dto.FamilyActionPlanCreationDTO;
import com.cuidar.dto.FamilyActionPlanItemDTO;
import com.cuidar.dto.FamilyActionPlanItemsListDTO;
import com.cuidar.dto.FamilyActionPlanSummaryDTO;
import com.cuidar.model.FamilyActionPlanItem;
import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberNoYesFlag;
import com.cuidar.service.FamilyActionPlanService;
import com.cuidar.service.FindMainFamilyMemberService;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("familyactionplan")
public class FamilyActionPlanController {
    
    private FamilyActionPlanService familyActionPlanService;  
    private FindMainFamilyMemberService findMainFamilyMemberService;  
    private ModelMapper modelMapper;

    public FamilyActionPlanController(ModelMapper modelMapper, FamilyActionPlanService familyActionPlanService, FindMainFamilyMemberService findMainFamilyMemberService) {
        this.familyActionPlanService = familyActionPlanService;
        this.findMainFamilyMemberService = findMainFamilyMemberService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/{id}/create")
    public ResponseEntity<Object> actionPlanItemCreate(@PathVariable(name = "id") UUID mainFamilyMemberId, @Valid @RequestBody FamilyActionPlanCreationDTO familyActionPlanCreationDTO){
        
        this.familyActionPlanService.createItemInActionPlan(mainFamilyMemberId, familyActionPlanCreationDTO);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/actions")
    public ResponseEntity<FamilyActionPlanItemsListDTO> getActionPlanItems(@PathVariable(name = "id") UUID mainFamilyMemberId){
        FamilyActionPlanItemsListDTO actionPlanItemsListDTO = new FamilyActionPlanItemsListDTO(mainFamilyMemberId);
        FamilyActionPlanItemDTO familuActionPlamItemDTO = new FamilyActionPlanItemDTO();

        MainFamilyMember foundMainFamilyMember = this.findMainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId);
        Iterable<FamilyActionPlanItem> foundActionPlanItems = this.familyActionPlanService.findAllActionPlanItems(foundMainFamilyMember);

        for (FamilyActionPlanItem familyActionPlanItem : foundActionPlanItems) {
            actionPlanItemsListDTO.getActionList().add(familuActionPlamItemDTO.convertToDto(modelMapper, familyActionPlanItem));
            actionPlanItemsListDTO.setActionItemsCount(actionPlanItemsListDTO.getActionItemsCount() + 1);
        }
        return new ResponseEntity<>(actionPlanItemsListDTO, HttpStatus.OK);
    }

    @PostMapping("/{id}/update/{itemId}/{done}")
    public ResponseEntity<Object> updateActionPlanItem(@PathVariable(name = "id") UUID mainFamilyMemberId, @PathVariable(name="itemId") UUID actionPlanItemId, @PathVariable(name="done") FamilyMemberNoYesFlag done){
        
        this.familyActionPlanService.updateActionPlanItem(mainFamilyMemberId, actionPlanItemId, done);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FamilyActionPlanSummaryDTO> getGeneralStatus(@PathVariable(name = "id") UUID mainFamilyMemberId){

        FamilyActionPlanSummaryDTO actionPlanItemsSummaryDTO = new FamilyActionPlanSummaryDTO(mainFamilyMemberId);
        FamilyActionPlanItemDTO familyActionPlamItemDTO = new FamilyActionPlanItemDTO();

        MainFamilyMember foundMainFamilyMember = this.findMainFamilyMemberService.findMainFamilyMemberById(mainFamilyMemberId);
        Iterable<FamilyActionPlanItem> foundActionPlanItems = this.familyActionPlanService.findAllActionPlanItems(foundMainFamilyMember);

        for (FamilyActionPlanItem familyActionPlanItem : foundActionPlanItems) {
            actionPlanItemsSummaryDTO.getActionList().add(familyActionPlamItemDTO.convertToDto(modelMapper, familyActionPlanItem));
            actionPlanItemsSummaryDTO.setActionItemsCount(actionPlanItemsSummaryDTO.getActionItemsCount() + 1);
            actionPlanItemsSummaryDTO.setConcludedActions(actionPlanItemsSummaryDTO.getConcludedActions() + (familyActionPlanItem.getDone() == FamilyMemberNoYesFlag.Yes ? 1 : 0));
        }

        return new ResponseEntity<>(actionPlanItemsSummaryDTO, HttpStatus.OK);
    }
}
