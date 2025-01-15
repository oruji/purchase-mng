package io.github.oruji.purchasemng.service.transaction.impl;

import io.github.oruji.purchasemng.entity.transaction.Transaction;
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

}
