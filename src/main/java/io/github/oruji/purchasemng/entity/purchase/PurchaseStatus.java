package io.github.oruji.purchasemng.entity.purchase;

import java.util.stream.Stream;

public enum PurchaseStatus {

	INITIATED,
	REVERSED,
	VERIFIED;

	public static PurchaseStatus fromName(String name) {
		return Stream.of(values())
				.filter(status -> status.name().equals(name))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						String.format("No purchase status found for name: %s", name)));
	}
}
