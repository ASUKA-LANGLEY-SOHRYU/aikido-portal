package com.prosvirnin.trainersportal.service.auth;

/**
 * Логика работы с паролем
 */
public interface PasswordService {
    /**
     * Хеширует пароль
     * @param password пароль в исходном виде
     * @return хеш пароля
     */
    String hashPassword(String password);

    /**
     * Проверяет, соответствует-ли хэш незахешированного паролья захешированному.
     * @param rawPassword незахешированный пароль
     * @param hashedPassword захешированный пароль
     * @return true - пароли соответствуют, false - пароли не соответствуют
     */
    boolean verifyPassword(String rawPassword, String hashedPassword);
}
