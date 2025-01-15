package io.github.oruji.purchasemng.service.purchase;

import io.github.oruji.purchasemng.service.purchase.model.PurchaseModel;

public interface PurchaseService {

	PurchaseModel save(PurchaseModel model);

	PurchaseModel verify(String trackingCode);
}
