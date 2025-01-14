package ir.snapppay.purchasemng.controller;

import ir.snapppay.purchasemng.dto.AuthenticationRequest;
import ir.snapppay.purchasemng.dto.AuthenticationResponse;
import ir.snapppay.purchasemng.dto.UserDto;
import ir.snapppay.purchasemng.exception.UserIdAlreadyExistException;
import ir.snapppay.purchasemng.service.UserService;
import ir.snapppay.purchasemng.utility.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
@RequestMapping("/api/auth")
public class UserController {

	private final UserService service;

	private final AuthenticationManager authenticationManager;

	private final JwtUtil jwtUtil;

	private final UserDetailsService userDetailsService;

	@PostMapping("/add-user")
	public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
		if (service.findByUsername(userDto.getUsername())) {
			throw new UserIdAlreadyExistException("Username is already taken");
		}
		return service.save(userDto);
	}

	@PostMapping("/token")
	public ResponseEntity<AuthenticationResponse> getToken(
			@RequestBody AuthenticationRequest request) throws BadCredentialsException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					request.getUsername(), request.getPassword()));
		} catch (BadCredentialsException badCredentialsException) {
			log.error("Incorrect username or password");
			throw badCredentialsException;
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}

}
