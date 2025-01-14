package ir.snapppay.purchasemng.service;

import ir.snapppay.purchasemng.dto.UserDto;

import org.springframework.http.ResponseEntity;

public interface UserService {

	ResponseEntity<UserDto> save(UserDto dto);

	boolean findByUsername(String username);

}
