package com.prosvirnin.trainersportal.model.dto.technique;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TechniqueListItem {
    private Long id;
    private String name;
    private String description;
}
