package com.josthi.web.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OTPGenTest {

	@ParameterizedTest
	@ValueSource(ints = { 1, 4, 10 })
	void generateRandomStringHonoursRequestedLength(int length) {
		assertEquals(length, OTPGen.generateRandomString(length).length());
	}

	@RepeatedTest(20)
	void generateRandomStringReturnsUpperCaseLettersOnly() {
		String value = OTPGen.generateRandomString(16);

		assertTrue(value.matches("[A-Z]{16}"), value);
	}

	@ParameterizedTest
	@ValueSource(ints = { 1, 4, 10 })
	void generateRandomNumberHonoursRequestedLength(int length) {
		assertEquals(length, OTPGen.generateRandomNumber(length).length());
	}

	@RepeatedTest(20)
	void generateRandomNumberReturnsDigitsOnly() {
		String value = OTPGen.generateRandomNumber(6);

		assertTrue(value.matches("[0-9]{6}"), value);
	}

	@Test
	void zeroLengthProducesEmptyValues() {
		assertEquals("", OTPGen.generateRandomString(0));
		assertEquals("", OTPGen.generateRandomNumber(0));
	}
}
