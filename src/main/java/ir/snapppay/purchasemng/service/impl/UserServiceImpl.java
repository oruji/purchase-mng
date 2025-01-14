package ir.snapppay.purchasemng.service.impl;

import java.util.Optional;

import ir.snapppay.purchasemng.model.User;
import ir.snapppay.purchasemng.repository.UserRepository;
import ir.snapppay.purchasemng.service.UserService;
import ir.snapppay.purchasemng.service.mapper.UserServiceMapper;
import ir.snapppay.purchasemng.service.model.UserModel;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	private final UserServiceMapper mapper;

	private final PasswordEncoder passwordEncoder;

	@Override
	public UserModel save(UserModel model) {
		User user = mapper.toUser(model, passwordEncoder.encode(model.getPassword()));
		user = repository.save(user);
		return mapper.toUserModel(user);
	}

	@Override
	public boolean findByUsername(String username) {
		Optional<User> byUsername = repository.findByUsername(username);
		return byUsername.isPresent();
	}

}
