package com.cuidar.controller;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import com.cuidar.dto.AttendanceRecordDTO;
import com.cuidar.dto.AttendanceRegisterTemplateInfoDTO;
import com.cuidar.dto.AttendanceSummaryRecordDTO;
import com.cuidar.dto.PageableFamilyAttandanceReportDTO;
import com.cuidar.model.FamilyAttendanceRecord;
import com.cuidar.service.FamilyAttendanceService;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
@RequestMapping("attendance")
public class FamilyAttendanceController {
    
    private FamilyAttendanceService familyAttendanceService;

    public FamilyAttendanceController(FamilyAttendanceService familyAttendanceService) {
        this.familyAttendanceService = familyAttendanceService;
    }

    @GetMapping("/{id}/template")
    public ResponseEntity<AttendanceRegisterTemplateInfoDTO> template(@PathVariable(name = "id") UUID mainFamilyMemberId) {
        return new ResponseEntity<>(this.familyAttendanceService.generateFamilyAttendanceTemplate(mainFamilyMemberId), HttpStatus.OK);
    }
    
    @PostMapping("/{id}/register")
    public ResponseEntity<Object> register(@PathVariable(name = "id") UUID mainFamilyMemberId, @RequestBody @Valid AttendanceRecordDTO attendanceRecordDTO) {
        
        FamilyAttendanceRecord familyAttendanceRecord = new FamilyAttendanceRecord();
        familyAttendanceRecord.setNotes(attendanceRecordDTO.getNotes());
        familyAttendanceRecord.setAttendanceDateTime(attendanceRecordDTO.getAttendanceDateTime());

        HashSet<UUID> doneActionPlanItemIds = new HashSet<>();
        HashSet<UUID> linkedFamilyMemberIds = new HashSet<>();

        attendanceRecordDTO.getLinkedActionPlanItems().forEach(item -> doneActionPlanItemIds.add(item.getActionPlanItemId()));
        attendanceRecordDTO.getLinkedFamilyMembers().forEach(item -> linkedFamilyMemberIds.add(item.getFamilyMemberId()));

        this.familyAttendanceService.registerAttendanceRecord(mainFamilyMemberId, familyAttendanceRecord, doneActionPlanItemIds, linkedFamilyMemberIds);
        
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<PageableFamilyAttandanceReportDTO> history(   @PathVariable(name = "id") UUID mainFamilyMemberId,
                                                                        @RequestParam(defaultValue = "0") int page,
                                                                        @RequestParam(defaultValue = "10") int size) {

        Page<FamilyAttendanceRecord> pageAttendanceReport = this.familyAttendanceService.getAttendanceHistorByMainFamilyMember(mainFamilyMemberId, page, size);
        List<FamilyAttendanceRecord> foundAttendanceReportList;

        PageableFamilyAttandanceReportDTO pageableFamilyAttandanceReportDTO = new PageableFamilyAttandanceReportDTO();

        pageableFamilyAttandanceReportDTO.setCurrentPage(pageAttendanceReport.getNumber());
        pageableFamilyAttandanceReportDTO.setTotalPages(pageAttendanceReport.getTotalPages());
        pageableFamilyAttandanceReportDTO.setTotalItems(pageAttendanceReport.getTotalElements());

        foundAttendanceReportList = pageAttendanceReport.getContent();

        for (FamilyAttendanceRecord familyAttendanceRecord : foundAttendanceReportList) {
            
            AttendanceSummaryRecordDTO singleAttendanceSummaryRecordDTO = new AttendanceSummaryRecordDTO();
            singleAttendanceSummaryRecordDTO.setAttendanceDateTime(familyAttendanceRecord.getAttendanceDateTime());
            singleAttendanceSummaryRecordDTO.setNotes(familyAttendanceRecord.getNotes());
            singleAttendanceSummaryRecordDTO.setLinkedActionPlanItemsSummary(familyAttendanceRecord.getSummaryActionPlanItems());
            singleAttendanceSummaryRecordDTO.setLinkedFamilyMembersSummary(familyAttendanceRecord.getSummaryFamilyMembers());
    
            pageableFamilyAttandanceReportDTO.getAttendances().add(singleAttendanceSummaryRecordDTO);
        }

        return new ResponseEntity<>(pageableFamilyAttandanceReportDTO, HttpStatus.OK);
    }
    
}
