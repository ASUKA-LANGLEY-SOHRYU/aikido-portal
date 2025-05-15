package com.prosvirnin.trainersportal.model.dto.user;

import lombok.Data;

@Data
public class TrainerRegistrationRequest {
    private String login;
    private String password;
}
