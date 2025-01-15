package io.github.oruji.purchasemng.service.purchase.impl;

import java.util.UUID;

import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.exception.PurchaseNotFoundException;
import io.github.oruji.purchasemng.repository.purchase.PurchaseRepository;
import io.github.oruji.purchasemng.service.purchase.PurchaseService;
import io.github.oruji.purchasemng.service.purchase.mapper.PurchaseServiceMapper;
import io.github.oruji.purchasemng.service.purchase.model.PurchaseModel;
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
		Purchase purchase = mapper.toPurchase(model, UUID.randomUUID().toString());
		purchase = repository.save(purchase);
		return mapper.toPurchaseModel(purchase);
	}

	@Override
	public PurchaseModel verify(String trackingCode) {
		Purchase purchase = repository.findByTrackingCode(trackingCode)
				.orElseThrow(() -> new PurchaseNotFoundException("Purchase Not found!"));
		purchase.verify();
		purchase = repository.save(purchase);
		return mapper.toPurchaseModel(purchase);
	}

}
