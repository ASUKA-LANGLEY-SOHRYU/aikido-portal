package com.prosvirnin.trainersportal.service.user;

import com.prosvirnin.trainersportal.model.dto.user.TrainerInfo;

public interface TrainerService {
    TrainerInfo getById(Long id);
}
