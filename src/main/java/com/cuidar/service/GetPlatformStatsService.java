package com.cuidar.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.cuidar.dto.PlatformRecentStatsDTO;
import com.cuidar.dto.PlatformStatsFamiliesDTO;
import com.cuidar.dto.PlatformStatsGroupedAgeCountDTO;
import com.cuidar.dto.PlatformStatsGroupedGenderAndAgesCountDTO;
import com.cuidar.dto.PlatformRecentStatsByMonthDTO;
import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberGeneralStatus;
import com.cuidar.repository.DependentFamilyMemberRepo;
import com.cuidar.repository.FamilyAttendanceRecordRepo;
import com.cuidar.repository.FamilyStatusUpdateRecordRepo;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.springframework.stereotype.Service;

@Service
public class GetPlatformStatsService {

    private MainFamilyMemberRepo mainFamilyMemberRepo;
    private DependentFamilyMemberRepo dependentFamilyMemberRepo;
    private FamilyAttendanceRecordRepo familyAttendanceRecordRepo;
    private FamilyStatusUpdateRecordRepo familyStatusUpdateRecordRepo;

    public GetPlatformStatsService(MainFamilyMemberRepo mainFamilyMemberRepo,
            DependentFamilyMemberRepo dependentFamilyMemberRepo, FamilyAttendanceRecordRepo familyAttendanceRecordRepo,
            FamilyStatusUpdateRecordRepo familyStatusUpdateRecordRepo) {
        this.mainFamilyMemberRepo = mainFamilyMemberRepo;
        this.dependentFamilyMemberRepo = dependentFamilyMemberRepo;
        this.familyAttendanceRecordRepo = familyAttendanceRecordRepo;
        this.familyStatusUpdateRecordRepo = familyStatusUpdateRecordRepo;
    }

    public PlatformStatsFamiliesDTO getFamiliesStats() {

        PlatformStatsFamiliesDTO platformStatsFamiliesDTO = new PlatformStatsFamiliesDTO();

        Calendar nextMonth = Calendar.getInstance();
        nextMonth.add(Calendar.MONTH, 1);

        Calendar today = Calendar.getInstance();

        long mainFamilyMembersCount = this.mainFamilyMemberRepo.count();
        long dependentMembersCount = this.dependentFamilyMemberRepo.count();
        long lastMonthFamiliesCount = this.mainFamilyMemberRepo.countByassistenceDueDateBetween(today.getTime(), nextMonth.getTime());
        long expiredFamiliesCount = this.mainFamilyMemberRepo.countByassistenceDueDateLessThan(today.getTime());
        long pendingApprovalCount = this.mainFamilyMemberRepo.countByGeneralStatus(FamilyMemberGeneralStatus.PendingApproval);

        platformStatsFamiliesDTO.setFamiliesCount(mainFamilyMembersCount);
        platformStatsFamiliesDTO.setFamilyMembersCount(mainFamilyMembersCount + dependentMembersCount);
        platformStatsFamiliesDTO.setPendingApprovalCount(pendingApprovalCount);
        platformStatsFamiliesDTO.setLastMonthFamiliesCount(lastMonthFamiliesCount);
        platformStatsFamiliesDTO.setExpiredFamiliesCount(expiredFamiliesCount);

        this.groupByGenderAndAge(platformStatsFamiliesDTO);

        return platformStatsFamiliesDTO;
    }

    public PlatformRecentStatsDTO getAttendanceStats() {

        PlatformRecentStatsDTO platformStatsAttendancesDTO = new PlatformRecentStatsDTO();

        platformStatsAttendancesDTO.setTotalCount(this.familyAttendanceRecordRepo.count());

        LocalDate finalDate = LocalDate.now();
        LocalDate startDate = finalDate.withDayOfMonth(1);

        finalDate = finalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);

        long lastAttendancesTotal = 0;

        for (int i = 6; i > 0; i--) {

            Date dtFrom = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date dtTo = Date.from(finalDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

            long count = this.familyAttendanceRecordRepo.countByattendanceDateTimeBetween(dtFrom, dtTo);

            StringBuilder sb = new StringBuilder();
            sb.append(startDate.getMonthValue()).append("/").append(startDate.getYear());

            platformStatsAttendancesDTO.getGroupedMonthlyCount()
                    .add(0, new PlatformRecentStatsByMonthDTO(sb.toString(), count));

            startDate = startDate.minusMonths(1);
            finalDate = finalDate.withDayOfMonth(1).minusDays(1);

            lastAttendancesTotal += count;
        }

        platformStatsAttendancesDTO.setRecentCount(lastAttendancesTotal);

        return platformStatsAttendancesDTO;
    }

    public PlatformRecentStatsDTO getLastUpdates() {
        PlatformRecentStatsDTO platformStatsUpdatesDTO = new PlatformRecentStatsDTO();

        platformStatsUpdatesDTO.setTotalCount(
                this.familyStatusUpdateRecordRepo.countBycurrentStatus(FamilyMemberGeneralStatus.Promoted));

        LocalDate finalDate = LocalDate.now();
        LocalDate startDate = finalDate.withDayOfMonth(1);

        finalDate = finalDate.plusMonths(1).withDayOfMonth(1).minusDays(1);

        long lastAttendancesTotal = 0;

        for (int i = 6; i > 0; i--) {

            Date dtFrom = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date dtTo = Date.from(finalDate.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant());

            long count = this.familyStatusUpdateRecordRepo.countByupdateDateTimeBetweenAndcurrentStatus(dtFrom, dtTo,
                    FamilyMemberGeneralStatus.Promoted);

            StringBuilder sb = new StringBuilder();
            sb.append(startDate.getMonthValue()).append("/").append(startDate.getYear());

            platformStatsUpdatesDTO.getGroupedMonthlyCount()
                    .add(new PlatformRecentStatsByMonthDTO(sb.toString(), count));

            startDate = startDate.minusMonths(1);
            finalDate = finalDate.withDayOfMonth(1).minusDays(1);

            lastAttendancesTotal += count;
        }

        platformStatsUpdatesDTO.setRecentCount(lastAttendancesTotal);
        return platformStatsUpdatesDTO;
    }

    private void groupByGenderAndAge(PlatformStatsFamiliesDTO platformStatsFamiliesDTO) {
        List<Object[]> resultGrouped = this.mainFamilyMemberRepo.findFamilyMemberGenderAndAgeCount();
        HashMap<FamilyMemberGender, HashMap<Integer, Long>> groupedGenderMap = new HashMap<>();

        if (resultGrouped != null && !resultGrouped.isEmpty()) {
            for (Object[] object : resultGrouped) {

                // resultGrouped[0] = gender
                // resultGrouped[1] = birthDate
                // resultGrouped[2] = count

                FamilyMemberGender familyMemberGender = (FamilyMemberGender) object[0];
                Date birthDate = (Date) object[1];
                Long count = (Long) object[2];

                HashMap<Integer, Long> ageMap = new HashMap<>();
                int currentAge = this.getAgeFromDate(birthDate);

                if (groupedGenderMap.containsKey(familyMemberGender)) {

                    ageMap = groupedGenderMap.get(familyMemberGender);

                    if (ageMap.containsKey(currentAge)) {
                        count += ageMap.get(currentAge);
                    }
                } 

                ageMap.put(currentAge, count);
                groupedGenderMap.put(familyMemberGender, ageMap);
            }
            
            groupedGenderMap.forEach((key, value) -> { 
                PlatformStatsGroupedGenderAndAgesCountDTO familyGenderGrouped = new PlatformStatsGroupedGenderAndAgesCountDTO(key);
                
                value.forEach((childKey, childValue) -> {
                    familyGenderGrouped.getGroupedAges().add(new PlatformStatsGroupedAgeCountDTO(childKey, childValue));
                    familyGenderGrouped.setCount(familyGenderGrouped.getCount() + childValue);
                });

                platformStatsFamiliesDTO.getGroupedGendersAndAges().add(familyGenderGrouped);
            });
            
        }
    }

    private Integer getAgeFromDate(Date birthDate) {
        Date now = Calendar.getInstance().getTime();
        long diffInMillies = Math.abs(birthDate.getTime() - now.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return (int) (diff / 365);
    }
}
