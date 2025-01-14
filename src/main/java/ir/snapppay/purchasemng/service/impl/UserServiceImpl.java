package ir.snapppay.purchasemng.service.impl;

import ir.snapppay.purchasemng.exception.UserIdAlreadyExistException;
import ir.snapppay.purchasemng.model.User;
import ir.snapppay.purchasemng.repository.UserRepository;
import ir.snapppay.purchasemng.service.UserService;
import ir.snapppay.purchasemng.service.mapper.UserServiceMapper;
import ir.snapppay.purchasemng.service.model.UserModel;
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

}
