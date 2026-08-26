package com.josthi.web.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SecurityTest {

	@Test
	void encryptThenDecryptRoundTripsTheValue() throws Exception {
		String encrypted = Security.encrypt("user@example.com");

		assertNotEquals("user@example.com", encrypted);
		assertEquals("user@example.com", Security.decrypt(encrypted));
	}

	@Test
	void encryptionUsesTheBuiltInKeyAndIsDeterministic() throws Exception {
		assertEquals(Security.encrypt("1234"), Security.encrypt("1234"));
	}

	@Test
	void encryptedValueIsNotCompatibleWithADifferentAesSecret() throws Exception {
		assertNotEquals(AES.encrypt("1234", "maragache1234"), Security.encrypt("1234"));
	}

	@Test
	void encryptedValueMatchesAesWithTheSameSecret() throws Exception {
		assertEquals(AES.encrypt("1234", "maragache123"), Security.encrypt("1234"));
	}

	@Test
	void decryptRejectsValueThatIsNotBase64() {
		assertThrows(IllegalArgumentException.class, () -> Security.decrypt("not-base64!!"));
	}
}
