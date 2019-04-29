package com.screenmedia.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FuelRatesNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FuelRatesNotAvailableException(String message) {
		super(message);
	}
}
