package io.github.oruji.purchasemng.service.purchase.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.entity.purchase.PurchaseStatus;
import io.github.oruji.purchasemng.entity.transaction.Transaction;
import io.github.oruji.purchasemng.entity.transaction.TransactionType;
import io.github.oruji.purchasemng.entity.user.User;
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

	private final PurchaseRepository repository;

	private final PurchaseServiceMapper mapper;

	private final UserService userService;

	private final TransactionService transactionService;

	private final TransactionServiceMapper transactionServiceMapper;

	@Override
	@Transactional
	public PurchaseModel save(PurchaseModel model) {
		User user = userService.findByUsername(model.getUser().getUsername());
		user.withdraw(model.getAmount());
		user = userService.save(user);
		Purchase purchase = mapper.toPurchase(model, UUID.randomUUID().toString(), user);
		purchase = repository.save(purchase);
		Transaction transaction = transactionServiceMapper.toTransaction(user, purchase, model.getAmount(),
				TransactionType.PURCHASE);
		transaction.setTrackingCode(UUID.randomUUID().toString());
		transaction.pending();
		transactionService.save(transaction);
		return mapper.toPurchaseModel(purchase);
	}

	@Override
	public PurchaseModel verify(String trackingCode) {
		Purchase purchase = repository.findByTrackingCode(trackingCode)
				.orElseThrow(() -> new PurchaseNotFoundException("Purchase Not found!"));
		if (!purchase.isVerfied()) {
			purchase.verify();
			purchase = repository.save(purchase);
			Transaction transaction = transactionService.findByPurchase(purchase);
			transaction.successful();
			transactionService.save(transaction);
		}
		return mapper.toPurchaseModel(purchase);
	}

	@Override
	public List<PurchaseModel> getPossibleReverses(PurchaseStatus status, LocalDateTime localDateTime,
			Pageable pageable) {
		List<Purchase> purchases = repository.findByStatusAndModificationDateIsBefore(status, localDateTime);
		return mapper.toPurchaseModels(purchases);
	}

	@Override
	@Transactional
	public void reverse(PurchaseModel model) {
		Purchase purchase = repository.findByTrackingCode(model.getTrackingCode())
				.orElseThrow(() -> new PurchaseNotFoundException("Purchase Not found!"));
		User user = purchase.getUser();
		user.deposit(purchase.getAmount());
		user = userService.save(user);
		purchase.setUser(user);
		purchase.reverse();
		repository.save(purchase);
		Transaction transaction = transactionService.findByPurchase(purchase);
		transaction.failed();
		transactionService.save(transaction);
	}

}
