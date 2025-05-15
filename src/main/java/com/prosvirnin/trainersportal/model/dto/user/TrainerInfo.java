package com.prosvirnin.trainersportal.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class TrainerInfo {
    private Long id;
    private String fullName;
    private Integer kyu;
    private LocalDate birthDate;
    private String phoneNumber;
}
