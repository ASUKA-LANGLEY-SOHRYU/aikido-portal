package com.prosvirnin.trainersportal.model.dto.user;

import com.prosvirnin.trainersportal.model.domain.user.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class UserListItem {
    private Long id;
    private String fullName;
    private Set<Role> roles;
    private String phoneNumber;
    private LocalDate birthDate;
    private Integer kyu;
    private Integer schoolClass;
    private String city;
    private Long clubId;
    private String clubName;

    public UserListItem(Long id, String fullName, Set<Role> roles, String phoneNumber, LocalDate birthDate, Integer kyu, Integer schoolClass, String city, Long clubId, String clubName) {
        this.id = id;
        this.fullName = fullName;
        this.roles = roles;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.kyu = kyu;
        this.schoolClass = schoolClass;
        this.city = city;
        this.clubId = clubId;
        this.clubName = clubName;
    }
}
