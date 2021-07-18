package com.cuidar.controller;

import com.cuidar.dto.PlatformRecentStatsDTO;
import com.cuidar.dto.PlatformStatsFamiliesDTO;
import com.cuidar.service.GetPlatformStatsService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("stats")
public class PlatformStats {
    private GetPlatformStatsService getPlatformStatsService;

    public PlatformStats(GetPlatformStatsService getPlatformStatsService) {
        this.getPlatformStatsService = getPlatformStatsService;
    }

    @GetMapping("/families")
    public ResponseEntity<PlatformStatsFamiliesDTO> getStatsFamilies() {
        PlatformStatsFamiliesDTO platformStatsFamiliesDTO = this.getPlatformStatsService.getFamiliesStats();

        return new ResponseEntity<>(platformStatsFamiliesDTO, HttpStatus.OK);
    }

    @GetMapping("/attendances")
    public ResponseEntity<PlatformRecentStatsDTO> getStatsAttandances() {
        PlatformRecentStatsDTO platformStatsAttendancesDTO = this.getPlatformStatsService.getAttendanceStats();

        return new ResponseEntity<>(platformStatsAttendancesDTO, HttpStatus.OK);
    }

    @GetMapping("/promotions")
    public ResponseEntity<PlatformRecentStatsDTO> getPromotion() {
        PlatformRecentStatsDTO platformStatsAttendancesDTO = this.getPlatformStatsService.getLastUpdates();

        return new ResponseEntity<>(platformStatsAttendancesDTO, HttpStatus.OK);
    }
}
