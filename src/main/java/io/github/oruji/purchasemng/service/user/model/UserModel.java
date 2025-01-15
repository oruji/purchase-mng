package io.github.oruji.purchasemng.service.user.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class UserModel {

	private String username;

	private String password;

	private BigDecimal balance;

}
