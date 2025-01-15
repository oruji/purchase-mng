package io.github.oruji.purchasemng.service.purchase.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.entity.purchase.PurchaseStatus;
import io.github.oruji.purchasemng.exception.PurchaseNotFoundException;
import io.github.oruji.purchasemng.repository.purchase.PurchaseRepository;
import io.github.oruji.purchasemng.service.purchase.PurchaseService;
import io.github.oruji.purchasemng.service.purchase.mapper.PurchaseServiceMapper;
import io.github.oruji.purchasemng.service.purchase.model.PurchaseModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
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

	@Override
	public List<PurchaseModel> getPossibleReverses(PurchaseStatus status, LocalDateTime localDateTime,
			Pageable pageable) {
		List<Purchase> purchases = repository.findByStatusAndModificationDateIsBefore(status, localDateTime);
		return mapper.toPurchaseModels(purchases);
	}

	@Override
	public void reverse(PurchaseModel model) {
		Purchase purchase = repository.findByTrackingCode(model.getTrackingCode())
				.orElseThrow(() -> new PurchaseNotFoundException("Purchase Not found!"));
		purchase.reverse();
		repository.save(purchase);
	}

}
