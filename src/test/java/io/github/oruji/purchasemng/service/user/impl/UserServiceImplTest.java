package io.github.oruji.purchasemng.service.user.impl;

import java.math.BigDecimal;
import java.util.Optional;

import io.github.oruji.purchasemng.entity.transaction.Transaction;
import io.github.oruji.purchasemng.entity.transaction.TransactionStatus;
import io.github.oruji.purchasemng.entity.transaction.TransactionType;
import io.github.oruji.purchasemng.entity.user.User;
import io.github.oruji.purchasemng.exception.UserIdAlreadyExistException;
import io.github.oruji.purchasemng.repository.user.UserRepository;
import io.github.oruji.purchasemng.service.transaction.TransactionService;
import io.github.oruji.purchasemng.service.transaction.mapper.TransactionServiceMapper;
import io.github.oruji.purchasemng.service.transaction.mapper.TransactionServiceMapperImpl;
import io.github.oruji.purchasemng.service.user.UserService;
import io.github.oruji.purchasemng.service.user.mapper.UserServiceMapper;
import io.github.oruji.purchasemng.service.user.mapper.UserServiceMapperImpl;
import io.github.oruji.purchasemng.service.user.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({ UserServiceImpl.class, UserServiceMapperImpl.class, TransactionServiceMapperImpl.class })
class UserServiceImplTest {

	@Autowired
	private UserService userService;

	@Autowired
	private UserServiceMapper userServiceMapper;

	@Autowired
	private TransactionServiceMapper transactionServiceMapper;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private PasswordEncoder passwordEncoder;

	@MockBean
	private TransactionService transactionService;

	private UserModel userModel;

	private User user;

	@BeforeEach
	void setup() {
		userModel = createUserModel("test-user-name", "password");
		user = createUser("test-user-name", "encodedPassword");
	}

	@Test
	void givenDuplicateUser_whenRegister_thenThrowsError() {
		when(userRepository.findByUsername("test-user-name")).thenReturn(Optional.of(user));
		Exception exception = assertThrows(UserIdAlreadyExistException.class,
				() -> userService.register(userModel));
		assertEquals("user with the given username already exists", exception.getMessage());
		verify(userRepository).findByUsername("test-user-name");
		verify(userRepository, times(0)).save(any());
		verifyNoInteractions(passwordEncoder, transactionService);
	}

	@Test
	void givenNewUser_whenRegister_thenSuccess() {
		when(userRepository.findByUsername("password")).thenReturn(Optional.empty());
		when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
		when(userRepository.save(any())).thenReturn(user);

		userService.register(userModel);

		ArgumentCaptor<User> userCapture = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(userCapture.capture());
		assertEquals(new BigDecimal(100_000), userCapture.getValue().getBalance());
		assertEquals(new BigDecimal(100_000), userCapture.getValue().getInitialBalance());
		assertEquals("test-user-name", userCapture.getValue().getUsername());
		assertEquals("encodedPassword", userCapture.getValue().getPassword());

		ArgumentCaptor<Transaction> transactionCapture = ArgumentCaptor.forClass(Transaction.class);
		verify(transactionService).save(transactionCapture.capture());
		assertEquals(new BigDecimal(100_000), transactionCapture.getValue().getAmount());
		assertNotNull(transactionCapture.getValue().getTrackingCode());
		assertEquals(TransactionStatus.SUCCESSFUL, transactionCapture.getValue().getStatus());
		assertEquals(TransactionType.ALLOCATION, transactionCapture.getValue().getType()); ;
	}

	private User createUser(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setInitialBalance(new BigDecimal(100_000));
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