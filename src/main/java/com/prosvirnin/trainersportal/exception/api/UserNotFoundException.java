package com.prosvirnin.trainersportal.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends ApiException {
    public UserNotFoundException(String username) {
        super("User with username: " + username + " does not exist.");
    }
    public UserNotFoundException(Long id) {
        super("User with id: " + id + " does not exist.");
    }
}
