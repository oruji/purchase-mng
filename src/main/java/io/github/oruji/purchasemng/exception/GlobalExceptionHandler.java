package io.github.oruji.purchasemng.exception;


import java.util.ArrayList;
import java.util.List;

import io.github.oruji.purchasemng.dto.ApiResponse;
import io.github.oruji.purchasemng.dto.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static io.github.oruji.purchasemng.utility.ResponseUtil.createResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<ErrorResponse> errorResponses = new ArrayList<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> errorResponses.add(new ErrorResponse(error.getField(),
				error.getDefaultMessage())));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createResponse(errorResponses,
				HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler({ UserIdAlreadyExistException.class, PurchaseNotFoundException.class,
			PurchaseInappropriateStatusException.class, InsufficientBalanceException.class })
	public ResponseEntity<ApiResponse<Void>> handleCustomExceptions(Exception ex) {
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createResponse(List.of(error),
				HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse<Void>> handleBadCredentialsException(BadCredentialsException ex) {
		ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(createResponse(List.of(error),
				HttpStatus.UNAUTHORIZED));
	}

}