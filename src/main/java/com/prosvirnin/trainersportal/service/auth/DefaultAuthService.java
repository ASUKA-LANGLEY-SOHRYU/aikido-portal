package com.prosvirnin.trainersportal.service.auth;

import com.prosvirnin.trainersportal.core.mapper.Mapper;
import com.prosvirnin.trainersportal.exception.api.*;
import com.prosvirnin.trainersportal.model.domain.application.Application;
import com.prosvirnin.trainersportal.model.domain.application.ApplicationStatus;
import com.prosvirnin.trainersportal.model.domain.application.ApplicationType;
import com.prosvirnin.trainersportal.model.domain.jwt.RefreshToken;
import com.prosvirnin.trainersportal.model.domain.jwt.TokenPair;
import com.prosvirnin.trainersportal.model.domain.user.Role;
import com.prosvirnin.trainersportal.model.domain.user.User;
import com.prosvirnin.trainersportal.model.dto.application.CreateApplicationRequest;
import com.prosvirnin.trainersportal.model.dto.user.BossRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.ClientRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.LoginRequest;
import com.prosvirnin.trainersportal.model.dto.user.TrainerRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.admin.AdminRegistrationRequest;
import com.prosvirnin.trainersportal.repository.ApplicationRepository;
import com.prosvirnin.trainersportal.repository.UserRepository;
import com.prosvirnin.trainersportal.service.application.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final UserRepository userRepository;
    private final ApplicationService applicationService;
    private final Mapper<ClientRegistrationRequest, User> clientRegistrationRequestUserMapper;
    private final Mapper<TrainerRegistrationRequest, User> trainerRegistrationRequestUserMapper;
    private final Mapper<BossRegistrationRequest, User> bossRegistrationRequestUserMapper;
    private final Mapper<AdminRegistrationRequest, User> adminRegistrationRequestUserMapper;
    private final PasswordService passwordService;
    private final JWTService jwtService;

    @Override
    public TokenPair registerClient(UserDetails userDetails, ClientRegistrationRequest clientRegistrationRequest) {
        if (userRepository.findByLogin(clientRegistrationRequest.getLogin()).isPresent())
            throw new UserAlreadyExistsException(clientRegistrationRequest.getLogin());
        User user = clientRegistrationRequestUserMapper.map(clientRegistrationRequest);
        user.setPassword(passwordService.hashPassword(clientRegistrationRequest.getPassword()));
        user.setIsApproved(false);
        setRegistrationDate(user);
        user.setRoles(Set.of(Role.CLIENT));
        user = userRepository.save(user);

        applicationService.createApplication(
                userDetails,
                CreateApplicationRequest.builder()
                        .applicationType(ApplicationType.REGISTRATION)
                        .clientId(user.getId())
                        .registrationRequest(clientRegistrationRequest)
                        .build()
        );

        return jwtService.generateTokenPair(user);
    }

    @Override
    public TokenPair registerTrainer(TrainerRegistrationRequest trainerRegistrationRequest) {
        if (userRepository.findByLogin(trainerRegistrationRequest.getLogin()).isPresent())
            throw new UserAlreadyExistsException(trainerRegistrationRequest.getLogin());
        User user = trainerRegistrationRequestUserMapper.map(trainerRegistrationRequest);
        user.setPassword(passwordService.hashPassword(trainerRegistrationRequest.getPassword()));
        setRegistrationDate(user);
        user.setRoles(Set.of(Role.TRAINER));
        userRepository.save(user);
        return jwtService.generateTokenPair(user);
    }

    @Override
    public TokenPair registerBoss(BossRegistrationRequest bossRegistrationRequest) {
        if (userRepository.findByLogin(bossRegistrationRequest.getLogin()).isPresent())
            throw new UserAlreadyExistsException(bossRegistrationRequest.getLogin());
        User user = bossRegistrationRequestUserMapper.map(bossRegistrationRequest);
        user.setPassword(passwordService.hashPassword(bossRegistrationRequest.getPassword()));
        setRegistrationDate(user);
        user.setRoles(Set.of(Role.BOSS));
        userRepository.save(user);
        return jwtService.generateTokenPair(user);
    }

    @Override
    public TokenPair registerAdmin(UserDetails userDetails, AdminRegistrationRequest registrationRequest) {
        boolean isAdmin = userDetails != null && userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter("ROLE_ADMIN"::equals)
                .count() == 1;

        if (userRepository.countByRole(Role.ADMIN) > 0 && !isAdmin) {
            throw new AccessDenied();
        }

        if (userRepository.findByLogin(registrationRequest.getLogin()).isPresent())
            throw new UserAlreadyExistsException(registrationRequest.getLogin());
        User user = adminRegistrationRequestUserMapper.map(registrationRequest);
        user.setPassword(passwordService.hashPassword(registrationRequest.getPassword()));
        setRegistrationDate(user);
        user.setRoles(Set.of(Role.ADMIN, Role.BOSS, Role.TRAINER, Role.CLIENT));
        userRepository.save(user);
        return jwtService.generateTokenPair(user);
    }

    @Override
    public TokenPair login(LoginRequest loginRequest) {
        User user = userRepository.findByLogin(loginRequest.getUsername())
                .orElseThrow(() -> new UserNotFoundException(loginRequest.getUsername()));
        if (!user.getIsApproved())
            throw new UserNotApprovedException(user.getUsername());
        if (!passwordService.verifyPassword(loginRequest.getPassword(), user.getPassword()))
            throw new IncorrectPasswordException();
        return jwtService.generateTokenPair(user);
    }

    @Override
    public TokenPair obtainTokenPair(RefreshToken refreshToken) {
        if (!jwtService.isValid(refreshToken))
            throw new InvalidRefreshTokenException();
        String username = jwtService.getSubject(refreshToken);
        User user = userRepository.findByLogin(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return jwtService.generateTokenPair(user);
    }

    private void setRegistrationDate(User user) {
        user.setRegistrationDate(LocalDate.now());
    }
}
