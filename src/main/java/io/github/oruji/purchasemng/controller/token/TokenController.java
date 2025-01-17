package io.github.oruji.purchasemng.controller.token;

import io.github.oruji.purchasemng.dto.ApiResponse;
import io.github.oruji.purchasemng.dto.token.TokenRequest;
import io.github.oruji.purchasemng.dto.token.TokenResponse;
import io.github.oruji.purchasemng.utility.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tokens")
public class TokenController {

	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;

	@PostMapping
	public ResponseEntity<ApiResponse<TokenResponse>> getToken(@Valid @RequestBody TokenRequest request) {
		log.info("Token generation request received for username: {}", request.getUsername());
		authenticateUser(request.getUsername(), request.getPassword());

		String token = generateJwtToken(request.getUsername());
		TokenResponse response = new TokenResponse(token);
		log.info("Token generated successfully for username: {}", request.getUsername());

		return createSuccessResponse(response);
	}

	private void authenticateUser(String username, String password) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (BadCredentialsException ex) {
			log.error("Incorrect username or password for user: {}", username);
			throw ex;
		}
	}

	private String generateJwtToken(String username) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		return jwtUtil.generateToken(userDetails);
	}

	private ResponseEntity<ApiResponse<TokenResponse>> createSuccessResponse(TokenResponse response) {
		ApiResponse<TokenResponse> apiResponse = new ApiResponse<>(
				HttpStatus.OK.value(),
				HttpStatus.OK.name(),
				response
		);
		return ResponseEntity.ok(apiResponse);
	}
}