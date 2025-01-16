package io.github.oruji.purchasemng.service.purchase.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.entity.purchase.PurchaseStatus;
import io.github.oruji.purchasemng.entity.transaction.Transaction;
import io.github.oruji.purchasemng.entity.transaction.TransactionType;
import io.github.oruji.purchasemng.entity.user.User;
import io.github.oruji.purchasemng.exception.PurchaseInappropriateStatusException;
import io.github.oruji.purchasemng.exception.PurchaseNotFoundException;
import io.github.oruji.purchasemng.repository.purchase.PurchaseRepository;
import io.github.oruji.purchasemng.service.purchase.PurchaseService;
import io.github.oruji.purchasemng.service.purchase.mapper.PurchaseServiceMapper;
import io.github.oruji.purchasemng.service.purchase.model.PurchaseModel;
import io.github.oruji.purchasemng.service.transaction.TransactionService;
import io.github.oruji.purchasemng.service.transaction.mapper.TransactionServiceMapper;
import io.github.oruji.purchasemng.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {

	private final PurchaseRepository purchaseRepository;

	private final PurchaseServiceMapper purchaseServiceMapper;

	private final UserService userService;

	private final TransactionService transactionService;

	private final TransactionServiceMapper transactionServiceMapper;

	@Override
	@Transactional
	public PurchaseModel order(PurchaseModel model) {
		User user = userService.findByUsername(model.getUser().getUsername());
		user.withdraw(model.getAmount());
		user = userService.save(user);

		Purchase purchase = purchaseServiceMapper.toPurchase(model, UUID.randomUUID().toString(), user);
		purchase = purchaseRepository.save(purchase);
		PurchaseModel purchaseModel = purchaseServiceMapper.toPurchaseModel(purchase);

		Transaction transaction = transactionServiceMapper.toTransaction(user, purchase, model.getAmount(),
				TransactionType.PURCHASE);
		transaction.setTrackingCode(UUID.randomUUID().toString());
		transaction.pending();
		transactionService.save(transaction);

		return purchaseModel;
	}

	@Override
	public PurchaseModel verify(String trackingCode) {
		Purchase purchase = purchaseRepository.findByTrackingCode(trackingCode)
				.orElseThrow(() -> new PurchaseNotFoundException(
						String.format("Purchase Not found! by trackingCode: %s", trackingCode)));

		if (purchase.isReversed()) {
			throw new PurchaseInappropriateStatusException(
					String.format("The purchase status: %s is not acceptable for verification.", purchase.getStatus())
			);
		}

		if (!purchase.isVerfied()) {
			purchase.verify();
			purchase = purchaseRepository.save(purchase);

			Transaction transaction = transactionService.findByPurchase(purchase);
			transaction.successful();
			transactionService.save(transaction);
		}
		return purchaseServiceMapper.toPurchaseModel(purchase);
	}

	@Override
	public List<PurchaseModel> getPossibleReverses(PurchaseStatus status, LocalDateTime localDateTime,
			Pageable pageable) {
		List<Purchase> purchases = purchaseRepository.findByStatusAndModificationDateIsBefore(status, localDateTime);
		return purchaseServiceMapper.toPurchaseModels(purchases);
	}

	@Override
	@Transactional
	public void reverse(PurchaseModel model) {
		Purchase purchase = purchaseRepository.findByTrackingCode(model.getTrackingCode())
				.orElseThrow(() -> new PurchaseNotFoundException(
						String.format("Purchase Not found by trackingCode: %s", model.getTrackingCode())));

		if (!purchase.isInitiated()) {
			throw new PurchaseInappropriateStatusException(
					String.format("The purchase status: %s is not acceptable for reverse.", purchase.getStatus())
			);
		}

		User user = purchase.getUser();
		user.deposit(purchase.getAmount());
		user = userService.save(user);

		purchase.setUser(user);
		purchase.reverse();
		purchaseRepository.save(purchase);

		Transaction transaction = transactionService.findByPurchase(purchase);
		transaction.failed();
		transactionService.save(transaction);
	}

}
