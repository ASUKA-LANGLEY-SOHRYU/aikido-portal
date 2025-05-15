package com.prosvirnin.trainersportal.core.mapper.technique;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.technique.Technique;
import com.prosvirnin.trainersportal.model.dto.technique.TechniqueDto;
import org.springframework.stereotype.Component;

@Component
public class TechniqueToTechniqueDtoMapper implements Mapper<Technique, TechniqueDto> {
    @Override
    public TechniqueDto map(Technique source) {
        return TechniqueDto.builder()
                .id(source.getId())
                .name(source.getName())
                .description(source.getDescription())
                .videoFilename(source.getTechniqueVideoDemonstration().getName())
                .build();
    }
}
