package com.prosvirnin.trainersportal.model.dto.application;

import com.prosvirnin.trainersportal.model.domain.application.ApplicationType;
import com.prosvirnin.trainersportal.model.dto.user.ClientRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.UserEditRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateApplicationRequest {
    private Long clientId;
    private ApplicationType applicationType;
    private ClientRegistrationRequest registrationRequest;
    private UserEditRequest editRequest;
}
