package io.github.oruji.purchasemng.config;

import lombok.RequiredArgsConstructor;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseProperties {

	private final Environment environment;

	public Long getPurchaseReverseTime() {
		return environment.getRequiredProperty("purchase.reverse.time", Long.class);
	}

	public Integer getPurchaseReversePageSize() {
		return environment.getRequiredProperty("purchase.reverse.page.size", Integer.class);
	}

}
