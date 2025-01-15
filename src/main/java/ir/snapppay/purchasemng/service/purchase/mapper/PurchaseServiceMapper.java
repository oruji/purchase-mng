package ir.snapppay.purchasemng.service.purchase.mapper;

import ir.snapppay.purchasemng.entity.purchase.Purchase;
import ir.snapppay.purchasemng.service.purchase.model.PurchaseModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PurchaseServiceMapper {

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "status", ignore = true)
	Purchase toPurchase(PurchaseModel model);

	PurchaseModel toPurchaseModel(Purchase purchase);

}
