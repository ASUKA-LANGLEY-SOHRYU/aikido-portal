package com.prosvirnin.trainersportal.model.dto.group;

import com.prosvirnin.trainersportal.model.domain.group.AgeGroup;
import lombok.Data;

import java.util.Collection;

@Data
public class JournalForTrainerDto {
    private Integer year;
    private Integer month;
    private Long clientId;
    private String clientName;
    private Integer kyu;
    private Long groupId;
    private String groupName;
    private AgeGroup ageGroup;
    private Long clubId;
    private String clubName;
    private String clubAddress;
    private Long trainingCountSummary;
    private Collection<ScheduleDto> schedule;
    private Collection<AttendanceDto> attendance;

    public JournalForTrainerDto(Integer year, Integer month, Long clientId, String clientName, Integer kyu, Long groupId, String groupName, AgeGroup ageGroup, Long clubId, String clubName, String clubAddress, Long trainingCountSummary) {
        this.year = year;
        this.month = month;
        this.clientId = clientId;
        this.clientName = clientName;
        this.kyu = kyu;
        this.groupId = groupId;
        this.groupName = groupName;
        this.ageGroup = ageGroup;
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubAddress = clubAddress;
        this.trainingCountSummary = trainingCountSummary;
    }
}
