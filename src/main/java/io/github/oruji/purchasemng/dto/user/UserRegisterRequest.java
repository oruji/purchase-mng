package io.github.oruji.purchasemng.dto.user;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterRequest {

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotNull
	private BigDecimal initialBalance;

}
