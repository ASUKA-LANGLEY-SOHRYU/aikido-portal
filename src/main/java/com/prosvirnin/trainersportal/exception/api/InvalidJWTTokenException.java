package com.prosvirnin.trainersportal.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class InvalidJWTTokenException extends ApiException {
    public InvalidJWTTokenException() {
        super("Invalid jwt token.");
    }
}
