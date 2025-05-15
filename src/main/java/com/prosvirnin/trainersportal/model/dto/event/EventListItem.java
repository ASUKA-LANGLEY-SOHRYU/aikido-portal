package com.prosvirnin.trainersportal.model.dto.event;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventListItem {
    private Long id;
    private String name;
    private String description;
}
