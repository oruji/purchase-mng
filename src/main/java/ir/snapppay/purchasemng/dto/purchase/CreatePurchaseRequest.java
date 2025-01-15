package ir.snapppay.purchasemng.dto.purchase;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePurchaseRequest {

	@NotNull
	private BigDecimal amount;

}
