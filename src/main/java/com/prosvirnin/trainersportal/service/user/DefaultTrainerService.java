package com.prosvirnin.trainersportal.service.user;

import com.prosvirnin.trainersportal.exception.api.NotFound;
import com.prosvirnin.trainersportal.model.dto.user.TrainerInfo;
import com.prosvirnin.trainersportal.repository.TrainerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultTrainerService implements TrainerService{
    private final TrainerRepository trainerRepository;

    @Override
    public TrainerInfo getById(Long id) {
        return trainerRepository.findInfoById(id)
                .orElseThrow( () -> new NotFound("Тренер с id " + id + " не найден"));
    }
}
