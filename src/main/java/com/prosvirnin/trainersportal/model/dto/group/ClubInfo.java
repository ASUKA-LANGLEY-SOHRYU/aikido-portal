package com.prosvirnin.trainersportal.model.dto.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
public class ClubInfo {
    private Long id;
    private String name;
    private String address;
    private Collection<Long> groups;

    public ClubInfo(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }
}
