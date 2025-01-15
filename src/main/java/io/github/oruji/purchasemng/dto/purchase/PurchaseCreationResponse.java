package io.github.oruji.purchasemng.dto.purchase;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PurchaseCreationResponse {

	private String trackingCode;

	private BigDecimal amount;

	private Integer status;

}
