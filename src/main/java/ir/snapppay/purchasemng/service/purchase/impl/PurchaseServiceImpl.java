package ir.snapppay.purchasemng.service.purchase.impl;

import ir.snapppay.purchasemng.entity.purchase.Purchase;
import ir.snapppay.purchasemng.repository.purchase.PurchaseRepository;
import ir.snapppay.purchasemng.service.purchase.PurchaseService;
import ir.snapppay.purchasemng.service.purchase.mapper.PurchaseServiceMapper;
import ir.snapppay.purchasemng.service.purchase.model.PurchaseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseRepository repository;

	private final PurchaseServiceMapper mapper;

	@Override
	public PurchaseModel save(PurchaseModel model) {
		Purchase purchase = mapper.toPurchase(model);
		purchase = repository.save(purchase);
		return mapper.toPurchaseModel(purchase);
	}

}
