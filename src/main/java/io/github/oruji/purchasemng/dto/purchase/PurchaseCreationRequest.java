package io.github.oruji.purchasemng.dto.purchase;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseCreationRequest {

	@NotNull
	private BigDecimal amount;

}
