package com.josthi.web.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.crypto.BadPaddingException;

import org.junit.jupiter.api.Test;

class AESTest {

	private static final String SECRET = "ssshhhhhhhhhhh!!!!";

	@Test
	void encryptThenDecryptRoundTripsTheValue() throws Exception {
		String encrypted = AES.encrypt("1234", SECRET);

		assertNotEquals("1234", encrypted);
		assertEquals("1234", AES.decrypt(encrypted, SECRET));
	}

	@Test
	void encryptionIsDeterministicForTheSameSecret() throws Exception {
		assertEquals(AES.encrypt("user@example.com", SECRET), AES.encrypt("user@example.com", SECRET));
	}

	@Test
	void differentSecretsProduceDifferentCipherText() throws Exception {
		assertNotEquals(AES.encrypt("1234", SECRET), AES.encrypt("1234", "another-secret"));
	}

	@Test
	void decryptWithWrongSecretFails() throws Exception {
		String encrypted = AES.encrypt("1234", SECRET);

		assertThrows(BadPaddingException.class, () -> AES.decrypt(encrypted, "another-secret"));
	}

	@Test
	void encryptSupportsEmptyAndUnicodeValues() throws Exception {
		assertEquals("", AES.decrypt(AES.encrypt("", SECRET), SECRET));
		assertEquals("সুশোভন", AES.decrypt(AES.encrypt("সুশোভন", SECRET), SECRET));
	}

	@Test
	void decryptRejectsValueThatIsNotBase64() {
		assertThrows(IllegalArgumentException.class, () -> AES.decrypt("not-base64!!", SECRET));
	}
}
