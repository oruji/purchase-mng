package io.github.oruji.purchasemng.service.user.impl;

import io.github.oruji.purchasemng.entity.user.User;
import io.github.oruji.purchasemng.exception.UserIdAlreadyExistException;
import io.github.oruji.purchasemng.exception.UserNotFoundException;
import io.github.oruji.purchasemng.repository.user.UserRepository;
import io.github.oruji.purchasemng.service.user.UserService;
import io.github.oruji.purchasemng.service.user.mapper.UserServiceMapper;
import io.github.oruji.purchasemng.service.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	private final UserServiceMapper mapper;

	private final PasswordEncoder passwordEncoder;

	@Override
	public UserModel save(UserModel model) {
		repository.findByUsername(model.getUsername()).ifPresent(user -> {
			log.warn("Duplicate user attempt for username: {}", model.getUsername());
			throw new UserIdAlreadyExistException("user with the given username already exists");
		});
		User user = mapper.toUser(model, passwordEncoder.encode(model.getPassword()));
		user = repository.save(user);
		return mapper.toUserModel(user);
	}

	@Override
	public User findByUsername(String username) {
		return repository.findByUsername(username)
				.orElseThrow(() -> new UserNotFoundException("There is not user for username: " + username));
	}

}
