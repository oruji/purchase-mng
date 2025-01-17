package io.github.oruji.purchasemng.utility;

import java.util.UUID;

public final class TextUtil {

	public static String generateTrackingCode() {
		return UUID.randomUUID().toString();
	}

}
