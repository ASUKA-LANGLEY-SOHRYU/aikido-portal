package com.prosvirnin.trainersportal.core.mapper.technique;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.technique.Technique;
import com.prosvirnin.trainersportal.model.dto.technique.TechniqueListItem;
import org.springframework.stereotype.Component;

@Component
public class TechniqueToTechniqueListItemMapper implements Mapper<Technique, TechniqueListItem> {
    @Override
    public TechniqueListItem map(Technique source) {
        return TechniqueListItem.builder()
                .id(source.getId())
                .description(source.getDescription())
                .name(source.getName())
                .build();
    }
}
