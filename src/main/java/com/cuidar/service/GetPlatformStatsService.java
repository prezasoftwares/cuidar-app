package com.cuidar.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.cuidar.dto.PlatformStatsAttendancesDTO;
import com.cuidar.dto.PlatformStatsFamiliesDTO;
import com.cuidar.dto.PlatformStatsGroupedAgeCountDTO;
import com.cuidar.dto.PlatformStatsGroupedGenderCountDTO;
import com.cuidar.dto.PlatformStatsGroupedMonthlyAttendanceCountDTO;
import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberGeneralStatus;
import com.cuidar.repository.DependentFamilyMemberRepo;
import com.cuidar.repository.FamilyAttendanceRecordRepo;
import com.cuidar.repository.MainFamilyMemberRepo;

import org.springframework.stereotype.Service;

@Service
public class GetPlatformStatsService {

    private MainFamilyMemberRepo mainFamilyMemberRepo;
    private DependentFamilyMemberRepo dependentFamilyMemberRepo;
    private FamilyAttendanceRecordRepo familyAttendanceRecordRepo;

    public GetPlatformStatsService(MainFamilyMemberRepo mainFamilyMemberRepo,
            DependentFamilyMemberRepo dependentFamilyMemberRepo,
            FamilyAttendanceRecordRepo familyAttendanceRecordRepo) {
        this.mainFamilyMemberRepo = mainFamilyMemberRepo;
        this.dependentFamilyMemberRepo = dependentFamilyMemberRepo;
        this.familyAttendanceRecordRepo = familyAttendanceRecordRepo;
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

        // total de famílias (membros principais)
        platformStatsFamiliesDTO.setFamiliesCount(mainFamilyMembersCount);
        // total de membros (principais + dependentes)
        platformStatsFamiliesDTO.setFamilyMembersCount(mainFamilyMembersCount + dependentMembersCount);

        platformStatsFamiliesDTO.setPendingApprovalCount(pendingApprovalCount);
        platformStatsFamiliesDTO.setLastMonthFamiliesCount(lastMonthFamiliesCount);
        platformStatsFamiliesDTO.setExpiredFamiliesCount(expiredFamiliesCount);

        this.groupByGender(platformStatsFamiliesDTO);

        return platformStatsFamiliesDTO;
    }

    public PlatformStatsAttendancesDTO getAttendanceStats() {

        PlatformStatsAttendancesDTO platformStatsAttendancesDTO = new PlatformStatsAttendancesDTO();

        platformStatsAttendancesDTO.setAttendancesCount(this.familyAttendanceRecordRepo.count());

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
            
            platformStatsAttendancesDTO.getGroupedMonthlyAttendances().add(new PlatformStatsGroupedMonthlyAttendanceCountDTO(sb.toString(), count));

            startDate = startDate.minusMonths(1);
            finalDate = finalDate.withDayOfMonth(1).minusDays(1);

            lastAttendancesTotal += count;
        }

        platformStatsAttendancesDTO.setRecentAttendancesCount(lastAttendancesTotal);
        return platformStatsAttendancesDTO;
    }

    private void groupByGender(PlatformStatsFamiliesDTO platformStatsFamiliesDTO) {
        // agrupamento por genero
        List<Object[]> result = this.mainFamilyMemberRepo.findFamilyMemberGenderCount();
        List<PlatformStatsGroupedGenderCountDTO> groupedGender = new ArrayList<>();

        if (result != null && !result.isEmpty()) {
            for (Object[] object : result) {
                groupedGender.add(new PlatformStatsGroupedGenderCountDTO((FamilyMemberGender)object[0], (Long)object[1]));
            }
        }
        platformStatsFamiliesDTO.setGroupedGenders(groupedGender);

        this.groupByAge(platformStatsFamiliesDTO);
    }

    private void groupByAge(PlatformStatsFamiliesDTO platformStatsFamiliesDTO){
        Date now = Calendar.getInstance().getTime();
        // agrupamento por faixa etária
        List<Object[]> result = this.mainFamilyMemberRepo.findFamilyMemberBirthDateCount();
        List<PlatformStatsGroupedAgeCountDTO> groupedBirthDate = new ArrayList<>();
        HashMap<Integer, Long> map = new HashMap<>();

        if (result != null && !result.isEmpty()) {
            for (Object[] object : result) {

                Date birthDate = (Date)object[0];

                long diffInMillies = Math.abs(birthDate.getTime() - now.getTime());
                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                int ticaTica = (int)(diff / 365);

                long currentCount = (Long)object[1];
                if (map.containsKey(ticaTica))
                {
                    currentCount += map.get(ticaTica);
                }

                map.put(ticaTica, currentCount);
            }

            map.forEach((key, value) -> {
                groupedBirthDate.add(new PlatformStatsGroupedAgeCountDTO(key, value));
            });
        }

        platformStatsFamiliesDTO.setGroupedAges(groupedBirthDate);
    }
}
