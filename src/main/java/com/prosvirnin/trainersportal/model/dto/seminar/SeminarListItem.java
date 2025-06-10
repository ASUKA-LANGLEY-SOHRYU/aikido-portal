package com.prosvirnin.trainersportal.model.dto.seminar;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SeminarListItem {
    private Long id;
    private String name;
    private LocalDateTime start;
    private String location;
}
