package com.prosvirnin.trainersportal.model.dto.seminar;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SeminarDto {
    private Long id;
    private String name;
    private LocalDateTime start;
    private String location;
    private String price;
    private String annualFeePrice;
    private String passportFeePrice;
    private String attestationFee5To2Kyu;
    private String attestationFee1Kyu;
    private String attestationFeeBlackBelt;
}
