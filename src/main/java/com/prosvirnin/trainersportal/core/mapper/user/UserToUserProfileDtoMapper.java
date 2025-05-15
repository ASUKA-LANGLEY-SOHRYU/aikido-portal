package com.prosvirnin.trainersportal.core.mapper.user;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.user.UserProfileDto;
import org.springframework.stereotype.Component;

@Component
public class UserToUserProfileDtoMapper implements Mapper<User, UserProfileDto> {
    @Override
    public UserProfileDto map(User source) {
        return UserProfileDto.builder()
                .id(source.getId())
                .roles(source.getRoles())
                .login(source.getLogin())
                .phoneNumber(source.getPhoneNumber())
                .fullName(source.getFullName())
                .birthDate(source.getBirthDate())
                .registrationDate(source.getRegistrationDate())
                .city(source.getCity())
                .kyu(source.getKyu())
                .attestationDate(source.getAttestationDate())
                .annualFee(source.getAnnualFee())
                .gender(source.getGender())
                .schoolClass(source.getSchoolClass())
                .parentName(source.getParentName())
                .parentPhone(source.getParentPhone())
                .build();
    }
}
