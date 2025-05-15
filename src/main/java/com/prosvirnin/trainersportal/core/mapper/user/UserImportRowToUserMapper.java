package com.prosvirnin.trainersportal.core.mapper.user;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.user.UserImportRow;
import org.springframework.stereotype.Component;

@Component
public class UserImportRowToUserMapper implements Mapper<UserImportRow, User> {
    @Override
    public User map(UserImportRow source) {
        return User.builder()
                .fullName(source.getFullName())
                .phoneNumber(source.getPhoneNumber())
                .birthDate(source.getBirthDate())
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
