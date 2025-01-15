package ir.snapppay.purchasemng.service.purchase;

import ir.snapppay.purchasemng.service.purchase.model.PurchaseModel;

public interface PurchaseService {

	PurchaseModel save(PurchaseModel model);

	PurchaseModel verify(String trackingCode);
}
