package io.github.oruji.purchasemng.controller.purchase.mapper;

import io.github.oruji.purchasemng.dto.purchase.PurchaseCreationRequest;
import io.github.oruji.purchasemng.dto.purchase.PurchaseCreationResponse;
import io.github.oruji.purchasemng.entity.purchase.PurchaseStatus;
import io.github.oruji.purchasemng.service.purchase.model.PurchaseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PurchaseControllerMapper {

	@Mapping(target = "status", ignore = true)
	@Mapping(target = "trackingCode", ignore = true)
	PurchaseModel toPurchaseModel(PurchaseCreationRequest request);

	PurchaseCreationResponse toCreatePurchaseResponse(PurchaseModel model);

	default PurchaseStatus toStatus(Integer statusCode) {
		if (statusCode == null) {
			return null;
		}
		return PurchaseStatus.fromValue(statusCode);
	}

	default Integer toStatusCode(PurchaseStatus status) {
		if (status == null) {
			return null;
		}
		return status.getValue();
	}

}
