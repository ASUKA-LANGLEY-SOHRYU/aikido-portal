package com.prosvirnin.trainersportal.service.auth;

import com.prosvirnin.trainersportal.model.domain.jwt.Token;
import com.prosvirnin.trainersportal.model.domain.jwt.TokenPair;
import com.prosvirnin.trainersportal.model.domain.user.User;

/**
 * Логика работы с JWT
 */
public interface JWTService {
    /**
     * Генерирует пару токенов, основываясь на пользователе
     * @param user пользователь
     * @return пара токенов
     */
    TokenPair generateTokenPair(User user);

    /**
     * Проверяет, является-ли токен валидным
     * @param token JWT
     * @return true - токен валидный, false - токен не валидный
     */
    boolean isValid(Token token);

    /**
     * Получение субъекта токена
     * @param token JWT
     * @return субьект токена
     */
    String getSubject(Token token);
}
