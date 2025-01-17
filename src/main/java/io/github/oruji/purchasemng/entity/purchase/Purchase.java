package io.github.oruji.purchasemng.entity.purchase;

import java.math.BigDecimal;
import java.util.List;

import io.github.oruji.purchasemng.entity.BaseEntity;
import io.github.oruji.purchasemng.entity.user.User;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
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

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "purchase_items", joinColumns = @JoinColumn(name = "purchase_id"))
	private List<PurchaseItem> items;

	public void verify() {
		this.setStatus(PurchaseStatus.VERIFIED);
	}

	public void reverse() {
		this.setStatus(PurchaseStatus.REVERSED);
	}

	public Boolean isVerified() {
		return PurchaseStatus.VERIFIED.equals(status);
	}

	public Boolean isInitiated() {
		return PurchaseStatus.INITIATED.equals(status);
	}

	public Boolean isReversed() {
		return PurchaseStatus.REVERSED.equals(status);
	}

}
