package com.prosvirnin.trainersportal.model.dto.user;

import com.prosvirnin.trainersportal.model.domain.user.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserImportRow {
    private String fullName;
    private String phoneNumber;
    private LocalDate birthDate;
    private String city;
    private Integer kyu;
    private LocalDate attestationDate;
    private Integer annualFee;
    private Gender gender;
    private Integer schoolClass;
    private String parentName;
    private String parentPhone;
    private String login;
    private String password;
}