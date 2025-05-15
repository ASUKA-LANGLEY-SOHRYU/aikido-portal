package com.prosvirnin.trainersportal.service.application;

import com.prosvirnin.trainersportal.exception.api.IAmATeapotException;
import com.prosvirnin.trainersportal.exception.api.NotFound;
import com.prosvirnin.trainersportal.exception.api.UserNotFoundException;
import com.prosvirnin.trainersportal.model.domain.application.Application;
import com.prosvirnin.trainersportal.model.domain.application.ApplicationStatus;
import com.prosvirnin.trainersportal.model.domain.application.ApplicationType;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.application.ApplicationDto;
import com.prosvirnin.trainersportal.model.dto.application.CreateApplicationRequest;
import com.prosvirnin.trainersportal.model.dto.application.ReplyApplicationRequest;
import com.prosvirnin.trainersportal.repository.ApplicationRepository;
import com.prosvirnin.trainersportal.repository.UserRepository;
import com.prosvirnin.trainersportal.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefaultApplicationService implements ApplicationService{

    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    public Collection<ApplicationDto> findAllInProgressApplications() {
        Collection<ApplicationDto> applications = applicationRepository.findAllDtoByStatus(ApplicationStatus.IN_PROGRESS);
        applications.forEach(application -> {
            application.setRegistrationRequest(userRepository.getClientRegistrationRequestById(application.getClientId()));
        });

        return applications;
    }

    @Override
    @Transactional
    public void replyById(Long id, ReplyApplicationRequest request) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new NotFound("Заявка с id " + id + " не найдена."));

        if (application.getStatus() != ApplicationStatus.IN_PROGRESS ||
                request.getStatus() == ApplicationStatus.IN_PROGRESS)
            return;

        application.setStatus(request.getStatus());
        applicationRepository.save(application);

        if (request.getStatus() == ApplicationStatus.DECLINED)
            return;

        User client = application.getUser();

        if (application.getType() == ApplicationType.REGISTRATION) {
            client.setIsApproved(true);
        }

        if (application.getType() == ApplicationType.EDIT) {
            userService.editById(client.getId(), application.getUserEditRequest());
        }

        if (application.getType() == ApplicationType.REMOVE) {
            userService.deleteById(client.getId());
        }
        
    }

    @Override
    @Transactional
    public void createApplication(UserDetails userDetails, CreateApplicationRequest request) {
        User client = userRepository.findById(request.getClientId())
                        .orElseThrow(() -> new UserNotFoundException(request.getClientId()));

        User causedBy = userRepository.findByLogin(userDetails.getUsername())
                .orElseThrow(IAmATeapotException::new);

        applicationRepository.save(
                Application.builder()
                        .status(ApplicationStatus.IN_PROGRESS)
                        .type(request.getApplicationType())
                        .user(client)
                        .causedBy(causedBy)
                        .build()
        );
    }
}
