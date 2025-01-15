package io.github.oruji.purchasemng.service.purchase.mapper;

import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.service.purchase.model.PurchaseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PurchaseServiceMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "creationDate", ignore = true)
	@Mapping(target = "modificationDate", ignore = true)
	@Mapping(target = "trackingCode", source = "trackingCode")
	@Mapping(target = "amount", source = "model.amount")
	Purchase toPurchase(PurchaseModel model, String trackingCode);

	PurchaseModel toPurchaseModel(Purchase purchase);

}
