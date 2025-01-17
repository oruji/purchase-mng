package io.github.oruji.purchasemng.service.purchase.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.entity.purchase.PurchaseStatus;
import io.github.oruji.purchasemng.entity.transaction.Transaction;
import io.github.oruji.purchasemng.entity.transaction.TransactionType;
import io.github.oruji.purchasemng.entity.user.User;
import io.github.oruji.purchasemng.exception.InsufficientBalanceException;
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
	public PurchaseModel order(PurchaseModel purchaseModel) {
		log.info("Attempting to place an order for user: {}", purchaseModel.getUser().getUsername());

		User user = userService.findByUsername(purchaseModel.getUser().getUsername());
		validateSufficientBalance(purchaseModel.getAmount(), user);
		withdrawUserBalance(user, purchaseModel.getAmount());

		Purchase purchase = createAndSavePurchase(purchaseModel, user);
		createAndSaveTransaction(user, purchase, purchaseModel.getAmount());

		log.info("Order placed successfully with tracking code: {}", purchase.getTrackingCode());
		return purchaseServiceMapper.toPurchaseModel(purchase);
	}

	private static void validateSufficientBalance(BigDecimal purchaseAmount, User user) {
		if (user.getBalance().compareTo(purchaseAmount) < 0) {
			throw new InsufficientBalanceException(String.format("Insufficient balance for user: %s",
					user.getUsername()));
		}
	}

	private void withdrawUserBalance(User user, BigDecimal amount) {
		user.withdraw(amount);
		userService.save(user);
	}

	private Purchase createAndSavePurchase(PurchaseModel model, User user) {
		Purchase purchase = purchaseServiceMapper.toPurchase(model, UUID.randomUUID().toString(), user);
		return purchaseRepository.save(purchase);
	}

	private void createAndSaveTransaction(User user, Purchase purchase, BigDecimal amount) {
		Transaction transaction = transactionServiceMapper.toTransaction(user, purchase, amount,
				TransactionType.PURCHASE);
		transaction.setTrackingCode(UUID.randomUUID().toString());
		transaction.pending();
		transactionService.save(transaction);
	}

	@Override
	public PurchaseModel verify(String trackingCode) {
		log.info("Attempting to verify purchase with tracking code: {}", trackingCode);

		Purchase purchase = findByTrackingCode(trackingCode);

		validatePurchaseStatus(purchase);

		if (!purchase.isVerified()) {
			purchase.verify();
			purchase = purchaseRepository.save(purchase);

			successAndSaveTransaction(purchase);

			log.info("Purchase with tracking code: {} verified successfully", trackingCode);
		} else {
			log.info("Purchase with tracking code: {} is already verified", trackingCode);
		}

		return purchaseServiceMapper.toPurchaseModel(purchase);
	}

	private void successAndSaveTransaction(Purchase purchase) {
		Transaction transaction = transactionService.findByPurchase(purchase);
		transaction.successful();
		transactionService.save(transaction);
	}

	private static void validatePurchaseStatus(Purchase purchase) {
		if (purchase.isReversed()) {
			log.error("Purchase with tracking code: {} is already reversed", purchase.getTrackingCode());
			throw new PurchaseInappropriateStatusException(
					String.format("The purchase status: %s is not acceptable for verification.", purchase.getStatus())
			);
		}
	}

	@Override
	public List<PurchaseModel> getPossibleReverses(PurchaseStatus status, LocalDateTime localDateTime,
			Pageable pageable) {
		log.info("Fetching possible reverses with status: {} and modification date before: {}", status, localDateTime);

		List<Purchase> purchases = purchaseRepository.findByStatusAndModificationDateIsBefore(status, localDateTime,
				pageable);
		return purchaseServiceMapper.toPurchaseModels(purchases);
	}

	@Override
	@Transactional
	public void reverse(PurchaseModel purchaseModel) {
		log.info("Attempting to reverse purchase with tracking code: {}", purchaseModel.getTrackingCode());

		Purchase purchase = findByTrackingCode(purchaseModel.getTrackingCode());
		validatePurchaseStatusToReverse(purchase);

		User user = depositAndSaveUser(purchase);

		reverseAndSavePurchase(purchase, user);

		failAndSaveTransaction(purchase);

		log.info("Purchase with tracking code: {} reversed successfully", purchaseModel.getTrackingCode());
	}

	private void reverseAndSavePurchase(Purchase purchase, User user) {
		purchase.setUser(user);
		purchase.reverse();
		purchaseRepository.save(purchase);
	}

	private User depositAndSaveUser(Purchase purchase) {
		User user = purchase.getUser();
		user.deposit(purchase.getAmount());
		user = userService.save(user);
		return user;
	}

	private Purchase findByTrackingCode(String purchaseTrackingCode) {
		return purchaseRepository.findByTrackingCode(purchaseTrackingCode)
				.orElseThrow(() -> {
					log.error("Purchase not found with tracking code: {}", purchaseTrackingCode);
					return new PurchaseNotFoundException("Purchase not found with tracking code: "
							+ purchaseTrackingCode);
				});
	}

	private static void validatePurchaseStatusToReverse(Purchase purchase) {
		if (!purchase.isInitiated()) {
			log.error("Purchase with tracking code: {} is not in an appropriate status for reversal",
					purchase.getTrackingCode());
			throw new PurchaseInappropriateStatusException(
					String.format("The purchase status: %s is not acceptable for reverse.", purchase.getStatus())
			);
		}
	}

	private void failAndSaveTransaction(Purchase purchase) {
		Transaction transaction = transactionService.findByPurchase(purchase);
		transaction.failed();
		transactionService.save(transaction);
	}
}