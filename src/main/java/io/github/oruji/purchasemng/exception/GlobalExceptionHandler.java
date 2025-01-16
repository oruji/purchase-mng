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

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<ErrorResponse> errorResponses = new ArrayList<>();
		ex.getBindingResult().getFieldErrors().forEach(error -> {
			errorResponses.add(new ErrorResponse(error.getField(), error.getDefaultMessage()));
		});
		ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
				errorResponses);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler({ UserIdAlreadyExistException.class, PurchaseNotFoundException.class })
	public ResponseEntity<ApiResponse<Void>> handleDuplicateUserException(UserIdAlreadyExistException ex) {
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
		ApiResponse<Void> response = new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(),
				List.of(error));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse<Void>> handleBadCredentialsException(BadCredentialsException ex) {
		ErrorResponse error = new ErrorResponse(HttpStatus.UNAUTHORIZED.getReasonPhrase(), ex.getMessage());
		ApiResponse<Void> response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(),
				List.of(error));
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	}

}