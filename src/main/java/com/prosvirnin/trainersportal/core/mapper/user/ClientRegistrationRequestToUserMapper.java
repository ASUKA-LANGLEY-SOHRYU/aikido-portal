package com.prosvirnin.trainersportal.core.mapper.user;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.user.ClientRegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class ClientRegistrationRequestToUserMapper implements Mapper<ClientRegistrationRequest, User> {

    @Override
    public User map(ClientRegistrationRequest source) {
        return User.builder()
                .login(source.getLogin())
                .password(source.getPassword())
                .fullName(source.getFullName())
                .parentPhone(source.getPhone())
                .gender(source.getGender())
                .schoolClass(source.getSchoolClass())
                .birthDate(source.getBirthDate())
                .city(source.getCity())
                .parentName(source.getParentName())
                .kyu(source.getKyu())
                .attestationDate(source.getAttestationDate())
                .build();
    }
}
