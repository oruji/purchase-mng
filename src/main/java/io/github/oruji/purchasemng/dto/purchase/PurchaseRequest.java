package io.github.oruji.purchasemng.dto.purchase;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PurchaseRequest {

	@NotNull
	private BigDecimal amount;

	List<PurchaseItemDto> items;

}
