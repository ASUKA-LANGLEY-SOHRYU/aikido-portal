package com.prosvirnin.trainersportal.core.mapper.group;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.group.Group;
import com.prosvirnin.trainersportal.model.dto.group.GroupCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class GroupCreateRequestToGroupMapper implements Mapper<GroupCreateRequest, Group> {
    @Override
    public Group map(GroupCreateRequest source) {
        return Group.builder()
                .name(source.getName())
                .ageGroup(source.getAgeGroup())
                .build();
    }
}
