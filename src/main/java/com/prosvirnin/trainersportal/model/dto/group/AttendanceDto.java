package com.prosvirnin.trainersportal.model.dto.group;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class AttendanceDto {
    private Long id;
    private Long groupId;
    private Long userId;
    private LocalDate date;
}
