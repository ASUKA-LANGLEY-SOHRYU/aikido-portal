package com.prosvirnin.trainersportal.service.user;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.exception.api.UserAlreadyExistsException;
import com.prosvirnin.trainersportal.exception.api.UserNotFoundException;
import com.prosvirnin.trainersportal.model.domain.group.Club;
import com.prosvirnin.trainersportal.model.domain.user.Gender;
import com.prosvirnin.trainersportal.model.domain.user.Role;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.user.*;
import com.prosvirnin.trainersportal.repository.UserRepository;
import com.prosvirnin.trainersportal.service.excel.UserImportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Mapper<User, UserProfileDto> userUserProfileDtoMapper;

    @Mock
    private UserImportService userImportService;

    @Mock
    private Mapper<UserImportRow, User> userImportRowUserMapper;

    @InjectMocks
    private DefaultUserService userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private User testUser;
    private UserProfileDto testUserProfileDto;
    private UserEditRequest testUserEditRequest;

    @BeforeEach
    void setUp() {

        testUser = new User();
        testUser.setId(1L);
        testUser.setLogin("testuser");
        testUser.setPassword("hashedpassword");
        testUser.setFullName("Test User");
        testUser.setPhoneNumber("1234567890");
        testUser.setBirthDate(LocalDate.of(1990, 1, 1));
        testUser.setCity("Test City");
        testUser.setKyu(5);
        testUser.setAttestationDate(LocalDate.of(2022, 1, 1));
        testUser.setAnnualFee(12345);
        testUser.setGender(Gender.MALE);
        testUser.setSchoolClass(10);
        testUser.setParentName("Parent Name");
        testUser.setParentPhone("0987654321");
        testUser.setRoles(Set.of(Role.CLIENT));


        testUserProfileDto = new UserProfileDto();
        testUserProfileDto.setId(1L);
        testUserProfileDto.setLogin("testuser");
        testUserProfileDto.setFullName("Test User");
        testUserProfileDto.setPhoneNumber("1234567890");
        testUserProfileDto.setBirthDate(LocalDate.of(1990, 1, 1));
        testUserProfileDto.setCity("Test City");
        testUserProfileDto.setKyu(5);
        testUserProfileDto.setAttestationDate(LocalDate.of(2022, 1, 1));
        testUserProfileDto.setAnnualFee(12345);
        testUserProfileDto.setGender(Gender.MALE);
        testUserProfileDto.setSchoolClass(10);
        testUserProfileDto.setParentName("Parent Name");
        testUserProfileDto.setParentPhone("0987654321");
        testUserProfileDto.setRoles(Set.of(Role.CLIENT));


        testUserEditRequest = new UserEditRequest();
        testUserEditRequest.setLogin("testuser");
        testUserEditRequest.setFullName("Updated User");
        testUserEditRequest.setPhoneNumber("9876543210");
        testUserEditRequest.setBirthDate(LocalDate.of(1990, 1, 1));
        testUserEditRequest.setCity("Updated City");
        testUserEditRequest.setKyu(6);
        testUserEditRequest.setAttestationDate(LocalDate.of(2023, 1, 1));
        testUserEditRequest.setAnnualFee(12345);
        testUserEditRequest.setSchoolClass(11);
        testUserEditRequest.setParentName("Updated Parent");
        testUserEditRequest.setParentPhone("1122334455");
    }

    @Test
    void loadUserByUsername_shouldReturnUser_whenUserExists() {

        when(userRepository.findByLogin(testUser.getLogin())).thenReturn(Optional.of(testUser));


        User result = userService.loadUserByUsername(testUser.getLogin());


        assertNotNull(result);
        assertEquals(testUser.getLogin(), result.getLogin());
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getFullName(), result.getFullName());
        verify(userRepository).findByLogin(testUser.getLogin());
    }

    @Test
    void loadUserByUsername_shouldThrowUserNotFoundException_whenUserDoesNotExist() {

        String nonExistentLogin = "nonexistent";
        when(userRepository.findByLogin(nonExistentLogin)).thenReturn(Optional.empty());


        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.loadUserByUsername(nonExistentLogin));
        verify(userRepository).findByLogin(nonExistentLogin);
    }

    @Test
    void save_shouldSaveUser() {

        when(userRepository.save(testUser)).thenReturn(testUser);


        userService.save(testUser);


        verify(userRepository).save(testUser);
    }

    @Test
    void findById_shouldReturnUser_whenUserExists() {

        when(userRepository.findById(testUser.getId())).thenReturn(Optional.of(testUser));


        User result = userService.findById(testUser.getId());


        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getLogin(), result.getLogin());
        assertEquals(testUser.getFullName(), result.getFullName());
        verify(userRepository).findById(testUser.getId());
    }

    @Test
    void findById_shouldThrowUserNotFoundException_whenUserDoesNotExist() {

        Long nonExistentId = 999L;
        when(userRepository.findById(nonExistentId)).thenReturn(Optional.empty());


        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.findById(nonExistentId));
        verify(userRepository).findById(nonExistentId);
    }

    @Test
    void findByLogin_shouldReturnUser_whenUserExists() {

        when(userRepository.findByLogin(testUser.getLogin())).thenReturn(Optional.of(testUser));


        User result = userService.findByLogin(testUser.getLogin());


        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals(testUser.getLogin(), result.getLogin());
        assertEquals(testUser.getFullName(), result.getFullName());
        verify(userRepository).findByLogin(testUser.getLogin());
    }

    @Test
    void findByLogin_shouldThrowUserNotFoundException_whenUserDoesNotExist() {

        String nonExistentLogin = "nonexistent";
        when(userRepository.findByLogin(nonExistentLogin)).thenReturn(Optional.empty());


        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.findByLogin(nonExistentLogin));
        verify(userRepository).findByLogin(nonExistentLogin);
    }

    @Test
    void deleteById_shouldDeleteUser_whenUserExists() {

        Long userId = testUser.getId();
        when(userRepository.existsById(userId)).thenReturn(true);


        userService.deleteById(userId);


        verify(userRepository).existsById(userId);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteById_shouldThrowUserNotFoundException_whenUserDoesNotExist() {

        Long nonExistentId = 999L;
        when(userRepository.existsById(nonExistentId)).thenReturn(false);


        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.deleteById(nonExistentId));
        verify(userRepository).existsById(nonExistentId);
        verify(userRepository, never()).deleteById(any());
    }

    @Test
    void deleteByLogin_shouldDeleteUser_whenUserExists() {

        String login = testUser.getLogin();
        when(userRepository.existsByLogin(login)).thenReturn(true);


        userService.deleteByLogin(login);


        verify(userRepository).existsByLogin(login);
        verify(userRepository).deleteByLogin(login);
    }

    @Test
    void deleteByLogin_shouldThrowUserNotFoundException_whenUserDoesNotExist() {

        String nonExistentLogin = "nonexistent";
        when(userRepository.existsByLogin(nonExistentLogin)).thenReturn(false);


        UserNotFoundException exception = assertThrows(UserNotFoundException.class,
                () -> userService.deleteByLogin(nonExistentLogin));
        verify(userRepository, never()).deleteByLogin(any());
    }

    @Test
    void getByUserDetails_shouldReturnUserProfileDto() {

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(testUser.getLogin());
        when(userRepository.findByLogin(testUser.getLogin())).thenReturn(Optional.of(testUser));
        when(userUserProfileDtoMapper.map(testUser)).thenReturn(testUserProfileDto);


        UserProfileDto result = userService.getByUserDetails(userDetails);


        assertNotNull(result);
        assertEquals(testUserProfileDto.getId(), result.getId());
        assertEquals(testUserProfileDto.getLogin(), result.getLogin());
        assertEquals(testUserProfileDto.getFullName(), result.getFullName());
        verify(userDetails).getUsername();
        verify(userRepository).findByLogin(testUser.getLogin());
        verify(userUserProfileDtoMapper).map(testUser);
    }

    @Test
    void editById_shouldThrowUserAlreadyExistsException_whenLoginAlreadyExists() {

        Long userId = testUser.getId();
        String newLogin = "existinguser";

        User existingUser = new User();
        existingUser.setId(2L);
        existingUser.setLogin(newLogin);

        UserEditRequest editRequest = new UserEditRequest();
        editRequest.setLogin(newLogin);

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRepository.findByLogin(newLogin)).thenReturn(Optional.of(existingUser));


        UserAlreadyExistsException exception = assertThrows(UserAlreadyExistsException.class,
                () -> userService.editById(userId, editRequest));
        verify(userRepository).findById(userId);
        verify(userRepository).findByLogin(newLogin);
        verify(userRepository, never()).save(any());
    }

    @Test
    void findAllListItems_shouldReturnUserListItems() {

        UserFilterRequest filterRequest = new UserFilterRequest();
        filterRequest.setCity("Test City");
        filterRequest.setKyu(5);
        filterRequest.setSchoolClass(10);
        filterRequest.setClubId(1L);
        filterRequest.setSearchQuery("test");

        User user = testUser;

        Club club = new Club();
        club.setId(1L);
        club.setName("Test Club");

        Object[] row = new Object[]{user, club};
        List<Object[]> rawData = new ArrayList<>();
        rawData.add(row);

        when(userRepository.findUserListItemsByFilter(
                filterRequest.getKyu(),
                filterRequest.getSchoolClass(),
                filterRequest.getCity(),
                filterRequest.getClubId(),
                filterRequest.getSearchQuery()
        )).thenReturn(rawData);


        Collection<UserListItem> result = userService.findAllListItems(filterRequest);


        assertNotNull(result);
        assertEquals(1, result.size());
        UserListItem item = result.iterator().next();
        assertEquals(user.getId(), item.getId());
        assertEquals(user.getFullName(), item.getFullName());
        assertEquals(user.getRoles(), item.getRoles());
        assertEquals(user.getPhoneNumber(), item.getPhoneNumber());
        assertEquals(user.getBirthDate(), item.getBirthDate());
        assertEquals(user.getKyu(), item.getKyu());
        assertEquals(user.getSchoolClass(), item.getSchoolClass());
        assertEquals(user.getCity(), item.getCity());
        assertEquals(club.getId(), item.getClubId());
        assertEquals(club.getName(), item.getClubName());

        verify(userRepository).findUserListItemsByFilter(
                filterRequest.getKyu(),
                filterRequest.getSchoolClass(),
                filterRequest.getCity(),
                filterRequest.getClubId(),
                filterRequest.getSearchQuery()
        );
    }

    @Test
    void setRolesById_shouldUpdateUserRoles() {

        Long userId = testUser.getId();
        Set<Role> newRoles = Set.of(Role.ADMIN, Role.TRAINER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));


        userService.setRolesById(userId, newRoles);


        verify(userRepository).findById(userId);
        verify(userRepository).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertEquals(newRoles, capturedUser.getRoles());
    }
}