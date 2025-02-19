package io.github.oruji.purchasemng.config;

import java.util.ArrayList;
import java.util.Optional;

import io.github.oruji.purchasemng.entity.user.User;
import io.github.oruji.purchasemng.repository.user.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class UserDetailsServiceConfig {

	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepository) {
		return username -> {
			Optional<User> user = userRepository.findByUsername(username);
			if (user.isEmpty()) {
				throw new UsernameNotFoundException("User not found!");
			}
			return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
					user.get().getPassword(), new ArrayList<>());
		};
	}

}

