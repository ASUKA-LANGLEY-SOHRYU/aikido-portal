package com.prosvirnin.trainersportal.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class InvalidAccessTokenException extends ApiException {
    public InvalidAccessTokenException() {
        super("Invalid jwt token.");
    }
}
