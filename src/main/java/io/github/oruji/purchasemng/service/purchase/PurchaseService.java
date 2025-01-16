package io.github.oruji.purchasemng.service.purchase;

import java.time.LocalDateTime;
import java.util.List;

import io.github.oruji.purchasemng.entity.purchase.PurchaseStatus;
import io.github.oruji.purchasemng.service.purchase.model.PurchaseModel;

import org.springframework.data.domain.Pageable;

public interface PurchaseService {

	PurchaseModel order(PurchaseModel model);

	PurchaseModel verify(String trackingCode);

	List<PurchaseModel> getPossibleReverses(PurchaseStatus status, LocalDateTime localDateTime, Pageable pageable);

	void reverse(PurchaseModel model);
}
