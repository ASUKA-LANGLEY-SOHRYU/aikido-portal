package com.prosvirnin.trainersportal.core.mapper.user;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.user.TrainerRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.admin.AdminRegistrationRequest;
import org.springframework.stereotype.Component;

@Component
public class TrainerRegistrationRequestToUserMapper implements Mapper<TrainerRegistrationRequest, User> {

    @Override
    public User map(TrainerRegistrationRequest source) {
        return User.builder()
                .login(source.getLogin())
                .password(source.getPassword())
                .build();
    }
}
