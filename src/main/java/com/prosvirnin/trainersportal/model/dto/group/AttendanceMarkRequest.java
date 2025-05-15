package com.prosvirnin.trainersportal.model.dto.group;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceMarkRequest {
    private Long groupId;
    private Long userId;
    private LocalDate date;
}
