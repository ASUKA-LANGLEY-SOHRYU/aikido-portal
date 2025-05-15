package com.prosvirnin.trainersportal.model.dto.group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupListItem {
    private Long id;
    private String name;
}
