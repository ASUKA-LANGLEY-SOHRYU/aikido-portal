package com.prosvirnin.trainersportal.service.group;

import com.prosvirnin.trainersportal.model.dto.group.ScheduleDto;
import com.prosvirnin.trainersportal.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultScheduleService implements ScheduleService{
    private final ScheduleRepository scheduleRepository;

    @Override
    public Collection<ScheduleDto> findAllByGroupId(Long groupId) {
        return scheduleRepository.findAllDtoByGroupId(groupId);
    }

    @Override
    public Collection<ScheduleDto> finAllByClubId(Long clubId) {
        return scheduleRepository.findAllDtoByClubId(clubId);
    }
}
