package io.github.oruji.purchasemng.entity.purchase;

import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PurchaseStatus {

	INITIATED(0), REVERSED(1), VERIFIED(2);

	private final Integer value;

	public static PurchaseStatus fromValue(int value) {
		return Stream.of(values())
				.filter(status -> status.value == value)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("no purchase status found for value: " + value));
	}

}
