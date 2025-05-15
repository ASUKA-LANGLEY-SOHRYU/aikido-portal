package com.prosvirnin.trainersportal.model.dto.user;

import lombok.Data;

@Data
public class UserFilterRequest {
    private String searchQuery;
    private Integer kyu;
    private Integer schoolClass;
    private String city;
    private Long clubId;
}
