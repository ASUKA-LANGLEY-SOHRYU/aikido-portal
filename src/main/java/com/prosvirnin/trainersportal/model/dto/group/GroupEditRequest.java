package com.prosvirnin.trainersportal.model.dto.group;

import com.prosvirnin.trainersportal.model.domain.group.AgeGroup;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
public class GroupEditRequest {
    private String name;
    private AgeGroup ageGroup;
    private Collection<ScheduleInGroupDto> schedule;
    private Long coachId;
    private Long clubId;
    private Collection<ExclusionDto> exclusions;
}
