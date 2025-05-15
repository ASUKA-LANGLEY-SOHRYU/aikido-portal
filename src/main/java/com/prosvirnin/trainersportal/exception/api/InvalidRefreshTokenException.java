package com.prosvirnin.trainersportal.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class InvalidRefreshTokenException extends ApiException {
    public InvalidRefreshTokenException() {
        super("Invalid refresh token.");
    }
}
