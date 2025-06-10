package com.prosvirnin.trainersportal.model.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    private Long authorId;
    private LocalDate publicationDate;
    private String city;
    private String name;
    private LocalDateTime start;
    private String description;
    private String filename;
}
