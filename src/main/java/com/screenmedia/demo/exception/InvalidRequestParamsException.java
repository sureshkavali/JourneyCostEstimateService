package com.screenmedia.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestParamsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidRequestParamsException(String message) {
		super(message);
	}

}
