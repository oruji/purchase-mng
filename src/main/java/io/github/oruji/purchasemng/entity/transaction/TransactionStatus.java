package io.github.oruji.purchasemng.entity.transaction;

import java.util.stream.Stream;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionStatus {

	PENDING,
	FAILED,
	SUCCESSFUL;

	public static TransactionStatus fromValue(String name) {
		return Stream.of(values())
				.filter(type -> type.name().equals(name))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("no transaction status found for value: " + name));
	}

}
