package io.github.oruji.purchasemng.entity.transaction;

import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionStatus {

	PENDING(0), FAILED(1), SUCCESSFUL(2);

	private final Integer value;

	public static TransactionStatus fromValue(int value) {
		return Stream.of(values())
				.filter(type -> type.value == value)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("no transaction status found for value: " + value));
	}

}
