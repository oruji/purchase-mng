package ir.snapppay.purchasemng.config;

import ir.snapppay.purchasemng.utility.JwtUtil;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfig {

	private final JwtUtil jwtUtil;

	private final UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(requests ->
						requests.requestMatchers(
										"/api/tokens",
										"/api/users",
										"/swagger-ui.html",
										"/swagger-ui/**",
										"/v3/api-docs",
										"/v3/api-docs/**").permitAll()
								.anyRequest()
								.authenticated()
				)
				.headers(headers -> headers.frameOptions(FrameOptionsConfig::sameOrigin)
				)
				.addFilter(new JwtAuthenticationFilter(authenticationManager(httpSecurity
						.getSharedObject(AuthenticationConfiguration.class)), jwtUtil))
				.addFilter(new JwtAuthorizationFilter(authenticationManager(httpSecurity
						.getSharedObject(AuthenticationConfiguration.class)), jwtUtil, userDetailsService));
		return httpSecurity.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
