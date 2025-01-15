package io.github.oruji.purchasemng.service.purchase.model;

import java.math.BigDecimal;

import io.github.oruji.purchasemng.entity.purchase.PurchaseStatus;
import io.github.oruji.purchasemng.service.user.model.UserModel;
import lombok.Data;

@Data
public class PurchaseModel {

	private String trackingCode;

	private BigDecimal amount;

	private PurchaseStatus status;

	private UserModel user;

}
