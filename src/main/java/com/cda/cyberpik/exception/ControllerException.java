package com.cda.cyberpik.exception;

import org.springframework.http.HttpStatus;

public class ControllerException extends Exception {
    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;

    public ControllerException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ControllerException(HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public ControllerException(Throwable cause) {
        super(cause);
    }

    public ControllerException() {
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
