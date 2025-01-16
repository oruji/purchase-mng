package io.github.oruji.purchasemng.controller.user;

import io.github.oruji.purchasemng.controller.user.mapper.UserControllerMapper;
import io.github.oruji.purchasemng.dto.ApiResponse;
import io.github.oruji.purchasemng.dto.user.CreateUserRequest;
import io.github.oruji.purchasemng.dto.user.CreateUserResponse;
import io.github.oruji.purchasemng.service.user.UserService;
import io.github.oruji.purchasemng.service.user.model.UserModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

	private final UserService userService;

	private final UserControllerMapper userControllerMapper;

	@Operation(summary = "Create a new user", description = "Creates a new user with the provided details.")
	@PostMapping()
	public ResponseEntity<ApiResponse<CreateUserResponse>> create(@Valid @RequestBody CreateUserRequest request) {
		log.info("Create User Api called with username: {}", request.getUsername());
		UserModel userModel = userService.register(userControllerMapper.toUserModel(request));
		CreateUserResponse response = userControllerMapper.toCreateUserResponse(userModel);
		log.info("User Created successfully");
		ApiResponse<CreateUserResponse> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.name(),
				response);
		return ResponseEntity.ok(apiResponse);
	}

}
