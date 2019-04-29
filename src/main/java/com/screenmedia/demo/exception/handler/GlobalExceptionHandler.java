package com.screenmedia.demo.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.screenmedia.demo.data.ErrorResponse;
import com.screenmedia.demo.exception.FuelRatesNotAvailableException;
import com.screenmedia.demo.exception.InvalidRequestParamsException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ FuelRatesNotAvailableException.class })
	public final ResponseEntity<ErrorResponse> handleFuelRatesNotAvailableException(FuelRatesNotAvailableException ex,
			WebRequest request) {
		ErrorResponse errorDetails = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ InvalidRequestParamsException.class })
	public final ResponseEntity<ErrorResponse> handleInvalidRequestParamsException(InvalidRequestParamsException ex,
			WebRequest request) {
		ErrorResponse errorDetails = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Throwable.class)
	public final ResponseEntity<Object> handleValidationException(Exception ex, WebRequest request) throws Exception {
		return super.handleException(ex, request);
	}
}
