package io.github.oruji.purchasemng.service.user.impl;

import java.math.BigDecimal;
import java.util.Optional;

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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

	@Test
	void givenDuplicateUser_whenSave_thenThrowsError() {
		UserModel userModel = createUserModel("test-user-name", "password");
		User user = createUser("test-user-name", "encodedPassword");
		when(userRepository.findByUsername("test-user-name")).thenReturn(Optional.of(user));
		Exception exception = assertThrows(UserIdAlreadyExistException.class,
				() -> userService.save(userModel));
		assertEquals("user with the given username already exists", exception.getMessage());
		verify(userRepository).findByUsername("test-user-name");
		verify(userRepository, times(0)).save(any());
		verifyNoInteractions(passwordEncoder, transactionService);
	}

	@Test
	void givenNewUser_whenSave_thenSuccess() {
		UserModel userModel = createUserModel("test-user-name", "password");
		User user = createUser("test-user-name", "encodedPassword");
		when(userRepository.findByUsername("password")).thenReturn(Optional.empty());
		when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
		when(userRepository.save(any())).thenReturn(user);

		UserModel resultUserModel = userService.save(userModel);

		ArgumentCaptor<User> userCapture = ArgumentCaptor.forClass(User.class);
		verify(userRepository).save(userCapture.capture());
		assertEquals(resultUserModel.getUsername(), userModel.getUsername());
		assertEquals(resultUserModel.getPassword(), "encodedPassword");
		assertEquals(resultUserModel.getInitialBalance(), userModel.getInitialBalance());
		assertEquals(userCapture.getValue().getBalance(), new BigDecimal(100_000));
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