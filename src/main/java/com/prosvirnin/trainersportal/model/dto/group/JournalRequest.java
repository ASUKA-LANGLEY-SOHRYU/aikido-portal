package com.prosvirnin.trainersportal.model.dto.group;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JournalRequest {
    private LocalDate yearAndMonthFrom;
    private LocalDate yearAndMonthTo;
    private Long groupId;
    private String sortBy = "date";
    private String direction = "asc";
}
