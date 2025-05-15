package com.prosvirnin.trainersportal.model.dto.group;

import com.prosvirnin.trainersportal.model.domain.group.AgeGroup;
import lombok.Data;

import java.util.Collection;

@Data
public class JournalForClientDto {
    private Integer year;
    private Integer month;
    private Long groupId;
    private String groupName;
    private AgeGroup ageGroup;
    private Long clubId;
    private String clubName;
    private String clubAddress;
    private long trainingCountSummary;
    private long attendanceCount;
    private Collection<ScheduleDto> schedule;
    private Collection<AttendanceDto> attendance;

    public JournalForClientDto(int year, int month, Long groupId, String groupName, AgeGroup ageGroup, Long clubId, String clubName, String clubAddress, long trainingCountSummary, long attendanceCount) {
        this.year = year;
        this.month = month;
        this.groupId = groupId;
        this.groupName = groupName;
        this.ageGroup = ageGroup;
        this.clubId = clubId;
        this.clubName = clubName;
        this.clubAddress = clubAddress;
        this.trainingCountSummary = trainingCountSummary;
        this.attendanceCount = attendanceCount;
    }
}
