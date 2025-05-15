package com.prosvirnin.trainersportal.model.dto.group;

import com.prosvirnin.trainersportal.model.domain.group.AgeGroup;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupDto {
    private Long id;
    private String name;
    private AgeGroup ageGroup;
    private String address;
    private Long clubId;
    private String clubName;
    private Long coachId;
    private String coachName;
}
