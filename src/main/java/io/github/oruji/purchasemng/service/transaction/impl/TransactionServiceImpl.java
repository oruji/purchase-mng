package io.github.oruji.purchasemng.service.transaction.impl;

import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.entity.transaction.Transaction;
import io.github.oruji.purchasemng.exception.PurchaseDoesntHaveTransactionException;
import io.github.oruji.purchasemng.repository.transaction.TransactionRepository;
import io.github.oruji.purchasemng.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository repository;

	@Override
	public Transaction save(Transaction transaction) {
		log.info("Saving transaction with tracking code: {}", transaction.getTrackingCode());
		Transaction savedTransaction = repository.save(transaction);
		log.info("Transaction saved successfully with tracking code: {}", savedTransaction.getTrackingCode());
		return savedTransaction;
	}

	@Override
	public Transaction findByPurchase(Purchase purchase) {
		log.info("Fetching transaction for purchase with tracking code: {}", purchase.getTrackingCode());
		return repository.findByPurchase(purchase)
				.orElseThrow(() -> {
					String errorMessage = String.format("No transaction found for purchase with tracking code: %s",
							purchase.getTrackingCode());
					log.error(errorMessage);
					throw new PurchaseDoesntHaveTransactionException(errorMessage);
				});
	}
}