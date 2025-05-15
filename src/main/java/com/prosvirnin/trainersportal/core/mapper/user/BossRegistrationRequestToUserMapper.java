package com.prosvirnin.trainersportal.core.mapper.user;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.user.BossRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.admin.AdminRegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class BossRegistrationRequestToUserMapper implements Mapper<BossRegistrationRequest, User> {

    @Override
    public User map(BossRegistrationRequest source) {
        return User.builder()
                .login(source.getLogin())
                .password(source.getPassword())
                .build();
    }
}
