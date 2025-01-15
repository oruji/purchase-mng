package ir.snapppay.purchasemng.controller.user;

import ir.snapppay.purchasemng.controller.user.mapper.UserControllerMapper;
import ir.snapppay.purchasemng.dto.user.CreateUserRequest;
import ir.snapppay.purchasemng.dto.user.CreateUserResponse;
import ir.snapppay.purchasemng.service.user.UserService;
import ir.snapppay.purchasemng.service.user.model.UserModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

	private final UserService service;

	private final UserControllerMapper mapper;

	@PostMapping("")
	public ResponseEntity<CreateUserResponse> create(@Valid @RequestBody CreateUserRequest request) {
		log.info("Create User Api called with username: {}", request.getUsername());
		UserModel userModel = service.save(mapper.toUserModel(request));
		CreateUserResponse response = mapper.toCreateUserResponse(userModel);
		log.info("User Created successfully");
		return ResponseEntity.ok(response);
	}

}
