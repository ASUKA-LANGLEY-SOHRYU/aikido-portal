package com.prosvirnin.trainersportal.service.auth;

import com.prosvirnin.trainersportal.model.domain.jwt.RefreshToken;
import com.prosvirnin.trainersportal.model.domain.jwt.TokenPair;
import com.prosvirnin.trainersportal.model.dto.user.BossRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.LoginRequest;
import com.prosvirnin.trainersportal.model.dto.user.ClientRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.TrainerRegistrationRequest;
import com.prosvirnin.trainersportal.model.dto.user.admin.AdminRegistrationRequest;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Регистрация, авторизация и продление токена.
 */
public interface AuthService {
    /**
     * Зарегистрировать клиента
     * @param userDetails пользователь, запросивший регистрацию
     * @param clientRegistrationRequest запрос на регистрацию клиента
     * @return пара токенов
     */
    TokenPair registerClient(UserDetails userDetails, ClientRegistrationRequest clientRegistrationRequest);

    /**
     * Зарегистрировать тренера
     * @param trainerRegistrationRequest запрос на регистрацию тренера
     * @return пара токенов
     */
    TokenPair registerTrainer(TrainerRegistrationRequest trainerRegistrationRequest);

    /**
     * Зарегистрировать руководителя
     * @param bossRegistrationRequest запрос на регистрацию руководителя
     * @return пара токенов
     */
    TokenPair registerBoss(BossRegistrationRequest bossRegistrationRequest);

    /**
     * Зарегистрировать администратора. Может только другой администратор, либо
     * если ещё нет администраторов в системе.
     * @param registrationRequest запрос на регистрацию администратора
     * @param userDetails текущий пользователь системы.
     * @return пара токенов
     */
    TokenPair registerAdmin(UserDetails userDetails, AdminRegistrationRequest registrationRequest);


    /**
     * Авторизация пользователя
     * @param loginRequest запрос на авторизацию
     * @return пара токенов
     */
    TokenPair login(LoginRequest loginRequest);

    /**
     * Обновление токенов
     * @param refreshToken refreshToken
     * @return пара токенов
     */
    TokenPair obtainTokenPair(RefreshToken refreshToken);
}
