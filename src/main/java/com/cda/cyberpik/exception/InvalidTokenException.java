package com.cda.cyberpik.exception;

import org.springframework.http.HttpStatus;

import javax.servlet.ServletException;

public class InvalidTokenException extends Exception {

	private static final long serialVersionUID = 1L;

	private HttpStatus httpStatus;

	public InvalidTokenException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public InvalidTokenException(HttpStatus httpStatus, String message, Throwable cause) {
		super(message, cause);
		this.httpStatus = httpStatus;
	}

	public InvalidTokenException(Throwable cause) {
		super(cause);
	}

	public InvalidTokenException() {
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}