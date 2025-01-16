package io.github.oruji.purchasemng.service.user.impl;

import java.util.UUID;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	private final UserServiceMapper mapper;

	private final PasswordEncoder passwordEncoder;

	private final TransactionService transactionService;

	private final TransactionServiceMapper transactionServiceMapper;

	@Override
	@Transactional
	public UserModel save(UserModel model) {
		repository.findByUsername(model.getUsername()).ifPresent(user -> {
			log.warn("Duplicate user attempt for username: {}", model.getUsername());
			throw new UserIdAlreadyExistException("user with the given username already exists");
		});
		User user = mapper.toUser(model, passwordEncoder.encode(model.getPassword()));
		user.deposit(model.getInitialBalance());
		user = repository.save(user);
		Transaction transaction = transactionServiceMapper.toTransaction(user, model.getInitialBalance(),
				TransactionType.ALLOCATION);
		transaction.successful();
		transaction.setTrackingCode(UUID.randomUUID().toString());
		transactionService.save(transaction);
		return mapper.toUserModel(user);
	}

	@Override
	public User findByUsername(String username) {
		return repository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("There is not user for username: " + username));
	}

	@Override
	public User save(User user) {
		return repository.save(user);
	}

}
