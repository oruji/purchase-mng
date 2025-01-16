package io.github.oruji.purchasemng.controller.token;

import io.github.oruji.purchasemng.dto.ApiResponse;
import io.github.oruji.purchasemng.dto.token.TokenRequest;
import io.github.oruji.purchasemng.dto.token.TokenResponse;
import io.github.oruji.purchasemng.dto.user.CreateUserResponse;
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

	@PostMapping()
	public ResponseEntity<ApiResponse<TokenResponse>> getToken(
			@Valid @RequestBody TokenRequest request) throws BadCredentialsException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException badCredentialsException) {
			log.error("Incorrect username or password");
			throw badCredentialsException;
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		final String token = jwtUtil.generateToken(userDetails);
		ApiResponse<TokenResponse> apiResponse = new ApiResponse<>(HttpStatus.OK.value(), HttpStatus.OK.name(),
				new TokenResponse(token));
		return ResponseEntity.ok(apiResponse);
	}

}
