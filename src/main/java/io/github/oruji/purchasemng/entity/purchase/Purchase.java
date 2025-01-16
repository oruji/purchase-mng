package io.github.oruji.purchasemng.entity.purchase;

import java.math.BigDecimal;

import io.github.oruji.purchasemng.entity.BaseEntity;
import io.github.oruji.purchasemng.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "PURCHASES")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Purchase extends BaseEntity {

	@NotNull
	@Column(nullable = false)
	private String trackingCode;

	@NotNull
	@Column(nullable = false)
	private BigDecimal amount;

	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PurchaseStatus status = PurchaseStatus.INITIATED;

	@NotNull
	@ManyToOne
	private User user;

	public void verify() {
		this.setStatus(PurchaseStatus.VERIFIED);
	}

	public void reverse() {
		this.setStatus(PurchaseStatus.REVERSED);
	}

	public Boolean isVerfied() {
		return PurchaseStatus.VERIFIED.equals(status);
	}

	public Boolean isInitiated() {
		return PurchaseStatus.INITIATED.equals(status);
	}

	public Boolean isReversed() {
		return PurchaseStatus.REVERSED.equals(status);
	}

}
