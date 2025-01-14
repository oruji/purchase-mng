package ir.snapppay.purchasemng.service.impl;

import java.util.Optional;

import ir.snapppay.purchasemng.dto.UserDto;
import ir.snapppay.purchasemng.model.User;
import ir.snapppay.purchasemng.repository.UserRepository;
import ir.snapppay.purchasemng.service.UserService;
import ir.snapppay.purchasemng.service.mapper.UserServiceMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository repository;

	private final UserServiceMapper mapper;

	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public ResponseEntity<UserDto> save(UserDto dto) {
		User user = mapper.toUser(dto, passwordEncoder.encode(dto.getPassword()));
		User savedUser = repository.save(user);
		dto.setPassword("******");
		return ResponseEntity.ok(dto);
	}

	@Override
	public boolean findByUsername(String username) {
		Optional<User> byUsername = repository.findByUsername(username);
		return byUsername.isPresent();
	}

}
