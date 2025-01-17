package io.github.oruji.purchasemng.utility;

import java.util.List;

import io.github.oruji.purchasemng.dto.ApiResponse;
import io.github.oruji.purchasemng.dto.ErrorResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class ResponseUtil {

	private ResponseUtil() {
		throw new UnsupportedOperationException("Utility class should not be instantiated!");
	}

	public static <T> ResponseEntity<ApiResponse<T>> createResponse(T response, HttpStatus status) {
		ApiResponse<T> apiResponse = new ApiResponse<>(
				status.value(),
				status.name(),
				response
		);
		return new ResponseEntity<>(apiResponse, status);
	}

	public static <T> ApiResponse<T> createResponse(List<ErrorResponse> errors, HttpStatus status) {
		return new ApiResponse<>(
				status.value(),
				status.name(),
				errors
		);
	}

}