package io.github.oruji.purchasemng.service.purchase.impl;

import java.math.BigDecimal;

import io.github.oruji.purchasemng.entity.purchase.Purchase;
import io.github.oruji.purchasemng.entity.purchase.PurchaseStatus;
import io.github.oruji.purchasemng.entity.transaction.Transaction;
import io.github.oruji.purchasemng.entity.transaction.TransactionStatus;
import io.github.oruji.purchasemng.entity.transaction.TransactionType;
import io.github.oruji.purchasemng.entity.user.User;
import io.github.oruji.purchasemng.repository.purchase.PurchaseRepository;
import io.github.oruji.purchasemng.service.purchase.PurchaseService;
import io.github.oruji.purchasemng.service.purchase.mapper.PurchaseServiceMapper;
import io.github.oruji.purchasemng.service.purchase.mapper.PurchaseServiceMapperImpl;
import io.github.oruji.purchasemng.service.purchase.model.PurchaseModel;
import io.github.oruji.purchasemng.service.transaction.TransactionService;
import io.github.oruji.purchasemng.service.transaction.mapper.TransactionServiceMapper;
import io.github.oruji.purchasemng.service.transaction.mapper.TransactionServiceMapperImpl;
import io.github.oruji.purchasemng.service.user.UserService;
import io.github.oruji.purchasemng.service.user.model.UserModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({ PurchaseServiceImpl.class, PurchaseServiceMapperImpl.class, TransactionServiceMapperImpl.class })
class PurchaseServiceImplTest {

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private PurchaseServiceMapper purchaseServiceMapper;

	@Autowired
	private TransactionServiceMapper transactionServiceMapper;

	@MockBean
	private UserService userService;

	@MockBean
	private PurchaseRepository purchaseRepository;

	@MockBean
	private TransactionService transactionService;

	@Test
	void givenNewPurchase_whenOrder_thenSuccess() {
		when(userService.findByUsername("my-test-user-name")).thenReturn(createUser("my-test-user-name", "password"));

		purchaseService.order(createPurchaseModel(new BigDecimal(10_000), "my-test-user-name"));

		ArgumentCaptor<User> userCapture = ArgumentCaptor.forClass(User.class);
		verify(userService).save(userCapture.capture());
		assertEquals(new BigDecimal(90_000), userCapture.getValue().getBalance());
		assertEquals(new BigDecimal(100_000), userCapture.getValue().getInitialBalance());
		assertEquals("my-test-user-name", userCapture.getValue().getUsername());
		assertEquals("password", userCapture.getValue().getPassword());

		ArgumentCaptor<Purchase> purchaseCapture = ArgumentCaptor.forClass(Purchase.class);
		verify(purchaseRepository).save(purchaseCapture.capture());
		assertEquals(new BigDecimal(10_000), purchaseCapture.getValue().getAmount());
		assertEquals(PurchaseStatus.INITIATED, purchaseCapture.getValue().getStatus());
		assertNotNull(purchaseCapture.getValue().getTrackingCode());

		ArgumentCaptor<Transaction> transactionCapture = ArgumentCaptor.forClass(Transaction.class);
		verify(transactionService).save(transactionCapture.capture());
		assertEquals(new BigDecimal(10_000), transactionCapture.getValue().getAmount());
		assertEquals(TransactionStatus.PENDING, transactionCapture.getValue().getStatus());
		assertEquals(TransactionType.PURCHASE, transactionCapture.getValue().getType());
		assertNotNull(transactionCapture.getValue().getTrackingCode());
	}

	private PurchaseModel createPurchaseModel(BigDecimal amount, String username) {
		PurchaseModel purchase = new PurchaseModel();
		purchase.setUser(createUserModel(username, "password"));
		purchase.setAmount(amount);
		return purchase;
	}

	private User createUser(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setInitialBalance(new BigDecimal(100_000));
		user.setBalance(new BigDecimal(100_000));
		return user;
	}

	private UserModel createUserModel(String username, String password) {
		UserModel user = new UserModel();
		user.setUsername(username);
		user.setPassword(password);
		user.setInitialBalance(new BigDecimal(100_000));
		return user;
	}

}