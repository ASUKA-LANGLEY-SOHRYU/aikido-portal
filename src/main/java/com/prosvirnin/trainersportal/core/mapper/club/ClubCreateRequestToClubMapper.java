package com.prosvirnin.trainersportal.core.mapper.club;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.group.Club;
import com.prosvirnin.trainersportal.model.dto.group.ClubCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class ClubCreateRequestToClubMapper implements Mapper<ClubCreateRequest, Club> {
    @Override
    public Club map(ClubCreateRequest source) {
        return Club.builder()
                .name(source.getName())
                .address(source.getAddress())
                .build();
    }
}
