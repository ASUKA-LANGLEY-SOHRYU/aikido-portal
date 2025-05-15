package com.prosvirnin.trainersportal.service.group;

import com.prosvirnin.trainersportal.model.dto.group.ScheduleDto;

import java.util.Collection;

public interface ScheduleService {
    Collection<ScheduleDto> findAllByGroupId(Long groupId);
    Collection<ScheduleDto> finAllByClubId(Long clubId);
}
