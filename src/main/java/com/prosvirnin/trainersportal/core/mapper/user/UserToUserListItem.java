package com.prosvirnin.trainersportal.core.mapper.user;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.user.UserListItem;
import org.springframework.stereotype.Component;

@Component
public class UserToUserListItem implements Mapper<User, UserListItem> {
    @Override
    public UserListItem map(User source) {
        return UserListItem.builder()
                .id(source.getId())
                .roles(source.getRoles())
                .phoneNumber(source.getPhoneNumber())
                .fullName(source.getFullName())
                .birthDate(source.getBirthDate())
                .city(source.getCity())
                .kyu(source.getKyu())
                .schoolClass(source.getSchoolClass())
                .build();
    }
}
