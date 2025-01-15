package io.github.oruji.purchasemng.service.purchase.mapper;

import java.util.List;

import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.entity.user.User;
import io.github.oruji.purchasemng.service.purchase.model.PurchaseModel;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface PurchaseServiceMapper {

	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "trackingCode", source = "trackingCode")
	@Mapping(target = "amount", source = "model.amount")
	@Mapping(target = "user", source = "user")
	Purchase toPurchase(PurchaseModel model, String trackingCode, User user);

	PurchaseModel toPurchaseModel(Purchase purchase);

	List<PurchaseModel> toPurchaseModels(List<Purchase> purchases);
}
