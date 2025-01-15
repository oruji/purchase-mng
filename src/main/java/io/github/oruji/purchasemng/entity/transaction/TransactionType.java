package io.github.oruji.purchasemng.entity.transaction;

import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionType {

	ALLOCATION(0), PURCHASE(1), REVERSE(2);

	private final Integer value;

	public static TransactionType fromValue(int value) {
		return Stream.of(values())
				.filter(type -> type.value == value)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("no transaction type found for value: " + value));
	}

}
