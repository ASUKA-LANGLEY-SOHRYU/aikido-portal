package com.prosvirnin.trainersportal.model.dto.application;

import com.prosvirnin.trainersportal.model.domain.application.ApplicationStatus;
import lombok.Data;

@Data
public class ReplyApplicationRequest {
    ApplicationStatus status;
}
