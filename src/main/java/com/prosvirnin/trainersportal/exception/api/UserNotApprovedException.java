package com.prosvirnin.trainersportal.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserNotApprovedException extends ApiException{
    public UserNotApprovedException(String username) {
        super("Пользователь " + username + " не одобрен администратором.");
    }
}
