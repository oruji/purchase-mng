package io.github.oruji.purchasemng.dto.purchase;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderResponse {

	private String trackingCode;

	private BigDecimal amount;

	private PurchaseStatusDto status;

}
