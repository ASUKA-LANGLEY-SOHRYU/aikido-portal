package com.prosvirnin.trainersportal.service.application;

import com.prosvirnin.trainersportal.exception.api.IAmATeapotException;
import com.prosvirnin.trainersportal.exception.api.UserNotFoundException;
import com.prosvirnin.trainersportal.model.domain.application.Application;
import com.prosvirnin.trainersportal.model.domain.application.ApplicationStatus;
import com.prosvirnin.trainersportal.model.domain.application.ApplicationType;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.application.ApplicationDto;
import com.prosvirnin.trainersportal.model.dto.application.CreateApplicationRequest;
import com.prosvirnin.trainersportal.model.dto.application.ReplyApplicationRequest;
import com.prosvirnin.trainersportal.model.dto.user.ClientRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.UserEditRequest;
import com.prosvirnin.trainersportal.repository.ApplicationRepository;
import com.prosvirnin.trainersportal.repository.UserRepository;
import com.prosvirnin.trainersportal.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApplicationServiceTest {

    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private DefaultApplicationService applicationService;

    @Mock
    private UserDetails userDetails;

    @Test
    void findAllInProgressApplications_shouldReturnDtosAndLoadRegistrationRequests() {
        ApplicationDto dto1 = ApplicationDto.builder()
                .clientId(1L)
                .build();

        List<ApplicationDto> mockDtos = List.of(dto1);

        when(applicationRepository.findAllDtoByStatus(ApplicationStatus.IN_PROGRESS)).thenReturn(mockDtos);
        when(userRepository.getClientRegistrationRequestById(1L)).thenReturn(ClientRegistrationRequest.builder()
                        .login("abc")
                .build());

        Collection<ApplicationDto> result = applicationService.findAllInProgressApplications();

        assertEquals(1, result.size());
        assertEquals("abc", result.iterator().next().getRegistrationRequest().getLogin());
    }

    @Test
    void replyById_shouldUpdateStatusAndCallEditOrDelete() {
        Application app = Application.builder()
                .id(1L)
                .status(ApplicationStatus.IN_PROGRESS)
                .type(ApplicationType.EDIT)
                .user(User.builder().id(10L).build())
                .userEditRequest(new UserEditRequest())
                .build();

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(app));

        ReplyApplicationRequest request = new ReplyApplicationRequest();
        request.setStatus(ApplicationStatus.APPROVED);

        applicationService.replyById(1L, request);

        verify(applicationRepository).save(app);
        verify(userService).editById(eq(10L), any());
    }

    @Test
    void replyById_shouldDoNothing_ifStatusNotInProgress() {
        Application app = Application.builder()
                .id(1L)
                .status(ApplicationStatus.APPROVED)
                .build();

        when(applicationRepository.findById(1L)).thenReturn(Optional.of(app));

        ReplyApplicationRequest request = new ReplyApplicationRequest();
        request.setStatus(ApplicationStatus.DECLINED);

        applicationService.replyById(1L, request);

        verify(applicationRepository, Mockito.never()).save(any());
    }

    @Test
    void createApplication_shouldSaveNewApplication() {
        User client = User.builder().id(1L).build();
        User causedBy = User.builder().id(2L).login("creator").build();

        CreateApplicationRequest request = CreateApplicationRequest.builder()
                .clientId(1L)
                .applicationType(ApplicationType.REGISTRATION)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(client));
        when(userDetails.getUsername()).thenReturn("creator");
        when(userRepository.findByLogin("creator")).thenReturn(Optional.of(causedBy));

        applicationService.createApplication(userDetails, request);

        verify(applicationRepository).save(argThat(app ->
                app.getUser().equals(client) &&
                        app.getCausedBy().equals(causedBy) &&
                        app.getStatus() == ApplicationStatus.IN_PROGRESS &&
                        app.getType() == ApplicationType.REGISTRATION
        ));
    }

    @Test
    void createApplication_shouldThrow_ifUserNotFound() {
        CreateApplicationRequest request = CreateApplicationRequest.builder()
                .clientId(99L)
                .build();

        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                applicationService.createApplication(userDetails, request));
    }

    @Test
    void createApplication_shouldThrow_ifCausedByNotFound() {
        CreateApplicationRequest request = CreateApplicationRequest.builder()
                .clientId(1L)
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(userDetails.getUsername()).thenReturn("missing_user");
        when(userRepository.findByLogin("missing_user")).thenReturn(Optional.empty());

        assertThrows(IAmATeapotException.class, () ->
                applicationService.createApplication(userDetails, request));
    }
}

