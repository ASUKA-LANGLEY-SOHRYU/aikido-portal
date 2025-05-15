package com.prosvirnin.trainersportal.exception.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.I_AM_A_TEAPOT)
public class IAmATeapotException extends ApiException{
    public IAmATeapotException() {
        super("Невозможное поведение.");
    }
}
