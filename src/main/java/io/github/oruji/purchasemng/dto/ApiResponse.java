package io.github.oruji.purchasemng.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

	private Integer status;

	private String message;

	private T data;

	private List<ErrorResponse> errors;

	public ApiResponse(Integer status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public ApiResponse(Integer status, String message, List<ErrorResponse> errors) {
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

}
