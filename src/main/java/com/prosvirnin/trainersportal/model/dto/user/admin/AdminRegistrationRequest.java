package com.prosvirnin.trainersportal.model.dto.user.admin;

import lombok.Data;

@Data
public class AdminRegistrationRequest {
    private String login;
    private String password;
}
