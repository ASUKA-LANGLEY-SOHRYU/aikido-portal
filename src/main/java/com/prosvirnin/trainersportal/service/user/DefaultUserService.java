package com.prosvirnin.trainersportal.service.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.exception.api.UserAlreadyExistsException;
import com.prosvirnin.trainersportal.exception.api.UserNotFoundException;
import com.prosvirnin.trainersportal.model.domain.group.Club;
import com.prosvirnin.trainersportal.model.domain.user.Role;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.user.*;
import com.prosvirnin.trainersportal.repository.UserRepository;
import com.prosvirnin.trainersportal.service.excel.UserImportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultUserService implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final Mapper<User, UserProfileDto> userUserProfileDtoMapper;
    private final UserImportService userImportService;
    private final Mapper<UserImportRow, User> userImportRowUserMapper;

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByLogin(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public User findByLogin(String login) throws UserNotFoundException {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException(login));
    }

    @Override
    public void deleteById(Long id) throws UserNotFoundException {
        if (!userRepository.existsById(id))
            throw new UserNotFoundException(id);
        userRepository.deleteById(id);
    }

    @Override
    public void deleteByLogin(String login) throws UserNotFoundException {
        if (!userRepository.existsByLogin(login))
            throw new UserNotFoundException(login);
        userRepository.deleteByLogin(login);
    }

    @Override
    public UserProfileDto getByUserDetails(UserDetails userDetails) {
        return userUserProfileDtoMapper.map(loadUserByUsername(userDetails.getUsername()));
    }

    @Override
    public UserProfileDto editByUserDetails(UserDetails userDetails, UserEditRequest userEditRequest) {
        User user = loadUserByUsername(userDetails.getUsername());
        editUser(user, userEditRequest);
        user = userRepository.save(user);
        return userUserProfileDtoMapper.map(user);
    }

    @Override
    public UserProfileDto editById(Long id, UserEditRequest userEditRequest) {
        User user = findById(id);
        editUser(user, userEditRequest);
        user = userRepository.save(user);
        return userUserProfileDtoMapper.map(user);
    }

    @Override
    public Collection<UserListItem> findAllListItems(UserFilterRequest request) {
        ObjectMapper om = new ObjectMapper();
        try {
            log.info(om.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<Object[]> rawData = userRepository.findUserListItemsByFilter(
                request.getKyu(),
                request.getSchoolClass(),
                request.getCity(),
                request.getClubId(),
                request.getSearchQuery()
        );

        return rawData.stream()
                .map(row -> {
                    User u = (User) row[0];
                    Club c = (Club) row[1];

                    return UserListItem.builder()
                            .id(u.getId())
                            .fullName(u.getFullName())
                            .roles(u.getRoles()) // теперь можно безопасно
                            .phoneNumber(u.getPhoneNumber())
                            .birthDate(u.getBirthDate())
                            .kyu(u.getKyu())
                            .schoolClass(u.getSchoolClass())
                            .city(u.getCity())
                            .clubId(c != null ? c.getId() : null)
                            .clubName(c != null ? c.getName() : null)
                            .build();
                })
                .distinct()
                .toList();
    }

    @Override
    public UserProfileDto getProfileById(Long id) {
        return userUserProfileDtoMapper.map(findById(id));
    }

    private void editUser(User user, UserEditRequest userEditRequest) {
        if (!Objects.equals(user.getLogin(), userEditRequest.getLogin()) && userRepository.findByLogin(userEditRequest.getLogin()).isPresent()){
            throw new UserAlreadyExistsException(userEditRequest.getLogin());
        }
        user.setLogin(userEditRequest.getLogin());
        user.setPhoneNumber(userEditRequest.getPhoneNumber());
        user.setFullName(userEditRequest.getFullName());
        user.setBirthDate(userEditRequest.getBirthDate());
        user.setCity(userEditRequest.getCity());
        user.setKyu(userEditRequest.getKyu());
        user.setAttestationDate(userEditRequest.getAttestationDate());
        user.setAnnualFee(userEditRequest.getAnnualFee());
        user.setSchoolClass(userEditRequest.getSchoolClass());
        user.setParentName(userEditRequest.getParentName());
        user.setParentPhone(userEditRequest.getParentPhone());
    }

    @Override
    public void setRolesById(Long id, Set<Role> roles) {
        User user = findById(id);

        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public void createClientsFromDatasheet(MultipartFile file) {
        List<UserImportRow> userRows = userImportService.parseExcel(file);
        Collection<User> users = userImportRowUserMapper.map(userRows);
        userRepository.saveAll(users);
    }

}
