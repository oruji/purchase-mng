package io.github.oruji.purchasemng.service.user.impl;

import java.math.BigDecimal;

import io.github.oruji.purchasemng.entity.transaction.Transaction;
import io.github.oruji.purchasemng.entity.transaction.TransactionType;
import io.github.oruji.purchasemng.entity.user.User;
import io.github.oruji.purchasemng.exception.UserIdAlreadyExistException;
import io.github.oruji.purchasemng.exception.UserNotFoundException;
import io.github.oruji.purchasemng.repository.user.UserRepository;
import io.github.oruji.purchasemng.service.transaction.TransactionService;
import io.github.oruji.purchasemng.service.transaction.mapper.TransactionServiceMapper;
import io.github.oruji.purchasemng.service.user.UserService;
import io.github.oruji.purchasemng.service.user.mapper.UserServiceMapper;
import io.github.oruji.purchasemng.service.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static io.github.oruji.purchasemng.utility.TextUtil.generateTrackingCode;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final UserServiceMapper userServiceMapper;

	private final PasswordEncoder passwordEncoder;

	private final TransactionService transactionService;

	private final TransactionServiceMapper transactionServiceMapper;

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username).orElseThrow(() -> createUserNotFoundException(username));
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional
	public UserModel register(UserModel userModel) {
		validateUserDoesNotExist(userModel.getUsername());

		User user = createUserFromModel(userModel);
		user.deposit(userModel.getInitialBalance());
		user = userRepository.save(user);

		createAndSaveInitialTransaction(user, userModel.getInitialBalance());

		return userServiceMapper.toUserModel(user);
	}

	private void validateUserDoesNotExist(String username) {
		userRepository.findByUsername(username).ifPresent(user -> {
			log.warn("Duplicate user attempt for username: {}", username);
			throw new UserIdAlreadyExistException("user with the given username already exists");
		});
	}

	private User createUserFromModel(UserModel model) {
		String encodedPassword = passwordEncoder.encode(model.getPassword());
		return userServiceMapper.toUser(model, encodedPassword);
	}

	private void createAndSaveInitialTransaction(User user, BigDecimal initialBalance) {
		Transaction transaction = transactionServiceMapper.toTransaction(user, initialBalance, TransactionType.ALLOCATION);
		transaction.successful();
		transaction.setTrackingCode(generateTrackingCode());
		transactionService.save(transaction);
	}

	private UserNotFoundException createUserNotFoundException(String username) {
		String errorMessage = String.format("User not found for username: %s", username);
		log.warn(errorMessage);
		return new UserNotFoundException(errorMessage);
	}

}
