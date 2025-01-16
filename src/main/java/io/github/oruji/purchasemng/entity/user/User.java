package io.github.oruji.purchasemng.entity.user;

import java.math.BigDecimal;

import io.github.oruji.purchasemng.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USERS")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

	@NotNull
	@Column(nullable = false, unique = true, length = 20)
	private String username;

	@NotNull
	@Column(nullable = false)
	private String password;

	@NotNull
	@Column(nullable = false)
	private BigDecimal initialBalance;

	@NotNull
	@Column(nullable = false)
	private BigDecimal balance = new BigDecimal(0);

	public void deposit(BigDecimal amount) {
		setBalance(balance.add(amount));
	}

	public void withdraw(BigDecimal amount) {
		balance = balance.subtract(amount);
	}

}
