package io.github.oruji.purchasemng.controller.purchase.mapper;

import io.github.oruji.purchasemng.dto.purchase.PurchaseCreationRequest;
import io.github.oruji.purchasemng.dto.purchase.PurchaseCreationResponse;
import io.github.oruji.purchasemng.service.purchase.model.PurchaseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PurchaseControllerMapper {

	@Mapping(target = "status", ignore = true)
	@Mapping(target = "trackingCode", ignore = true)
	@Mapping(target = "user.username", source = "username")
	PurchaseModel toPurchaseModel(PurchaseCreationRequest purchaseCreationRequest, String username);

	PurchaseCreationResponse toCreatePurchaseResponse(PurchaseModel purchaseModel);

}
