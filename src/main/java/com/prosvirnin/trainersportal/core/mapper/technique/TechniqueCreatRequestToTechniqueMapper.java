package com.prosvirnin.trainersportal.core.mapper.technique;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.technique.Technique;
import com.prosvirnin.trainersportal.model.dto.technique.TechniqueCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class TechniqueCreatRequestToTechniqueMapper implements Mapper<TechniqueCreateRequest, Technique> {
    @Override
    public Technique map(TechniqueCreateRequest source) {
        return Technique.builder()
                .name(source.getName())
                .description(source.getDescription())
                .build();
    }
}
