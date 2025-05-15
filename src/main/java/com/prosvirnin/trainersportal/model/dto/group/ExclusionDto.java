package com.prosvirnin.trainersportal.model.dto.group;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExclusionDto {
    private LocalDate date;
    private String status;
}
