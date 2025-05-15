package com.prosvirnin.trainersportal.model.dto.user;

import com.prosvirnin.trainersportal.model.domain.user.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClientRegistrationRequest {
    private String login;
    private String password;
    private String fullName;
    private String phone;
    private Gender gender;
    private Integer schoolClass;
    private LocalDate birthDate;
    private String city;
    private String parentName;
    private Integer kyu;
    private LocalDate attestationDate;
}
