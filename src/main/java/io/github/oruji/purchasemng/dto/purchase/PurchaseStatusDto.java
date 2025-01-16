package io.github.oruji.purchasemng.dto.purchase;

import java.util.stream.Stream;

public enum PurchaseStatusDto {

	INITIATED,
	REVERSED,
	VERIFIED;

	public static PurchaseStatusDto fromName(String name) {
		return Stream.of(values())
				.filter(status -> status.name().equals(name))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(
						String.format("No purchase status found for name: ", name)));
	}
}
