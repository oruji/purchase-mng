package io.github.oruji.purchasemng.service.transaction;

import java.util.Optional;

import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.entity.transaction.Transaction;

public interface TransactionService {

	Transaction save(Transaction transaction);

	Transaction findByPurchase(Purchase purchase);

}
