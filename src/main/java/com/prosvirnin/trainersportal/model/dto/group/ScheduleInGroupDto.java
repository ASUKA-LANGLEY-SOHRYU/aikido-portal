package com.prosvirnin.trainersportal.model.dto.group;

import com.prosvirnin.trainersportal.model.domain.group.DayOfWeek;
import lombok.Data;

import java.time.LocalTime;

@Data
public class ScheduleInGroupDto {
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
