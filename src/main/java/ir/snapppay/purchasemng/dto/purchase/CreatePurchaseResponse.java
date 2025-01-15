package ir.snapppay.purchasemng.dto.purchase;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CreatePurchaseResponse {

	private BigDecimal amount;

	private Integer status;

}
