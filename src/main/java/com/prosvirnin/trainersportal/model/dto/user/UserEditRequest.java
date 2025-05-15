package com.prosvirnin.trainersportal.model.dto.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserEditRequest {
    private String login;
    private String phoneNumber;
    private String fullName;
    private LocalDate birthDate;
    private String city;
    private Integer kyu;
    private LocalDate attestationDate;
    private Integer annualFee;
    private Integer schoolClass;
    private String parentName;
    private String parentPhone;
}
