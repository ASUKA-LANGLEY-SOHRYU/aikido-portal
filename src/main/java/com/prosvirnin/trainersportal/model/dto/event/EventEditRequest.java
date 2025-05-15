package com.prosvirnin.trainersportal.model.dto.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventEditRequest {
    private String city;
    private String name;
    private LocalDateTime start;
    private String description;
}
