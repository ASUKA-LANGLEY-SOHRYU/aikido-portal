package com.prosvirnin.trainersportal.model.dto.application;

import com.prosvirnin.trainersportal.model.domain.application.ApplicationType;
import com.prosvirnin.trainersportal.model.dto.user.ClientRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.UserEditRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApplicationDto {
    private Long id;
    private Long clientId;
    private String clientName;
    private String trainerName;
    private ApplicationType applicationType;
    private ClientRegistrationRequest registrationRequest;
    private UserEditRequest editRequest;

    public ApplicationDto(Long id, Long clientId, String clientName, String trainerName, ApplicationType applicationType, UserEditRequest editRequest) {
        this.id = id;
        this.clientId = clientId;
        this.clientName = clientName;
        this.trainerName = trainerName;
        this.applicationType = applicationType;
        this.editRequest = editRequest;
    }
}
