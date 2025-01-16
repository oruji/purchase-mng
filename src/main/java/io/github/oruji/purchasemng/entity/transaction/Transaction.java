package io.github.oruji.purchasemng.entity.transaction;

import java.math.BigDecimal;

import io.github.oruji.purchasemng.entity.BaseEntity;
import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TRANSACTIONS")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Transaction extends BaseEntity {

	@NotNull
	@Column(nullable = false)
	private String trackingCode;

	@NotNull
	@Column(nullable = false)
	private BigDecimal amount;

	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TransactionType type;

	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;

	@NotNull
	@ManyToOne
	private User user;

	@OneToOne
	private Purchase purchase;

	public void pending() {
		this.setStatus(TransactionStatus.PENDING);
	}

	public void failed() {
		this.setStatus(TransactionStatus.FAILED);
	}

	public void successful() {
		this.setStatus(TransactionStatus.SUCCESSFUL);
	}

}
