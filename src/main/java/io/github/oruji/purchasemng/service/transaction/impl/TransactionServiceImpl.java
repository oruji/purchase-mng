package io.github.oruji.purchasemng.service.transaction.impl;

import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.entity.transaction.Transaction;
import io.github.oruji.purchasemng.exception.PurchaseDoesntHaveTransactionException;
import io.github.oruji.purchasemng.repository.transaction.TransactionRepository;
import io.github.oruji.purchasemng.service.transaction.TransactionService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository repository;

	@Override
	public Transaction save(Transaction transaction) {
		return repository.save(transaction);
	}

	@Override
	public Transaction findByPurchase(Purchase purchase) {
		return repository.findByPurchase(purchase)
				.orElseThrow(() -> new PurchaseDoesntHaveTransactionException(
						String.format("There is no Transaction for purchase: %s", purchase.getTrackingCode())));
	}

}
