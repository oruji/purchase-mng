package ir.snapppay.purchasemng.service.purchase.model;

import java.math.BigDecimal;

import ir.snapppay.purchasemng.entity.purchase.PurchaseStatus;
import lombok.Data;

@Data
public class PurchaseModel {

	private BigDecimal amount;

	private PurchaseStatus status;

}
