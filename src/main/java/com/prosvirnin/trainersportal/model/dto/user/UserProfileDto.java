package com.prosvirnin.trainersportal.model.dto.user;

import com.prosvirnin.trainersportal.model.domain.user.Gender;
import com.prosvirnin.trainersportal.model.domain.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private Long id;
    private Set<Role> roles;
    private String login;
    private String phoneNumber;
    private String fullName;
    private LocalDate birthDate;
    private LocalDate registrationDate;
    private String city;
    private Integer kyu;
    private LocalDate attestationDate;
    private Integer annualFee;
    private Gender gender;
    private Integer schoolClass;
    private String parentName;
    private String parentPhone;
}
